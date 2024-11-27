
function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;
    document.body.appendChild(alertBox);
    // Using vanilla JavaScript to fade out the alert
    alertBox.style.display = 'block';
    setTimeout(() => {
        alertBox.style.opacity = 0;
        setTimeout(() => {
            alertBox.remove();
        }, 600);
    }, 3000);
}

function loadAccounts() {
    fetch('/admin/user')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(station => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td th:text="${station.id}"></td>
                        <td th:text="${station.name}"></td> 
                        <td th:text="${station.address}"></td> 
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" th:attr="data-id=${station.id}" onclick="editAccount(this)">Chỉnh sửa</button>
                        </td>
                    `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

function filterAccounts() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const accountID = row.cells[0].innerText.toLowerCase();  // Cột Mã Tài Khoản
        const accountName = row.cells[1].innerText.toLowerCase(); // Cột Tên Tài Khoản
        const customerID = row.cells[4].innerText.toLowerCase();  // Cột Mã Khách Hàng
        const status = row.cells[5].innerText.toLowerCase().trim(); // Cột Trạng Thái

        // Kiểm tra nếu searchInput có trong accountID, accountName hoặc customerID
        const matchesSearch =
            accountID.includes(searchInput) ||
            accountName.includes(searchInput) ||
            customerID.includes(searchInput);

        const matchesStatus =
            statusFilter === '' || // Nếu trạng thái là Tất cả
            (statusFilter === '1' && status === 'hoạt động') ||
            (statusFilter === '0' && status === 'ngưng hoạt động');

        // Nếu ô tìm kiếm không trống và không khớp với trạng thái
        if (searchInput && matchesSearch) {
            row.style.display = matchesStatus ? '' : 'none'; // Hiển thị hoặc ẩn dựa trên trạng thái
        } else if (!searchInput) {
            // Nếu ô tìm kiếm trống, chỉ cần kiểm tra trạng thái
            row.style.display = matchesStatus ? '' : 'none';
        } else {
            row.style.display = 'none'; // Ẩn hàng nếu không khớp với tìm kiếm
        }
    });
}

function showAddModal() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addStation() {
    // Lấy dữ liệu từ các trường input
    const nameStation = document.getElementById('newStationName').value;
    const addressStation = document.getElementById('newAddress').value;

    // Kiểm tra dữ liệu đầu vào
    if (!nameStation || !addressStation) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm tài khoản này không?");
    if (!confirmation) {
        return; // Nếu người dùng không xác nhận, dừng thao tác
    }

    // Tạo đối tượng dữ liệu tài khoản
    const stationData = {
        nameStation,
        addressStation
    };

    console.log('Dữ liệu gửi:', stationData);

    // Lấy CSRF token và header từ thẻ meta
    // const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    // const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    // console.log('CSRF Header:', csrfHeader);

    // Gửi yêu cầu POST tới server
    fetch('/admin-station/station/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // [csrfHeader]: csrfToken // Thêm CSRF token vào header
        },
        body: JSON.stringify(stationData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    showAlert('danger', errorData.message || 'Thêm trạm xe thất bại!');
                });
            }
            showAlert('success', 'Thêm trạm xe thành công!');
            loadAccounts(); // Tải lại danh sách trạm xe
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm trạm xe:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm trạm xe!');
        });
}

function deleteAccount(button) {
    const accountId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch('/admin/accounts/delete/${accountId}', { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa tài khoản thành công!');
                    // Disable the delete button instead of removing the row
                    loadAccounts()
                    clearFormSearch();

                } else {
                    showAlert('danger', 'Xóa tài khoản thất bại!');
                }
                confirmDeleteModal.hide();
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert('danger', 'Có lỗi xảy ra!');
                confirmDeleteModal.hide();
            });
    };
    confirmDeleteModal.show();
}

function editAccount(button) {
    // Lấy accountId từ button
    const accountId = button.getAttribute('data-id');
    console.log(accountId);

    // Fetch thông tin tài khoản từ API
    fetch(`/admin/user/${accountId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lấy thông tin tài khoản!');
            }
            return response.json();
        })
        .then(account => {
            // Gán giá trị vào các trường input trong modal
            document.getElementById('AccountId').value = account.id;
            document.getElementById('editAccountName').value = account.username;
            document.getElementById('editPassword').value = ""; // Không hiển thị mật khẩu
            document.getElementById('editFullName').value = account.full_name;
            document.getElementById('editPhoneNumber').value = account.phone_number;
            document.getElementById('editEmail').value = account.email;
            document.getElementById('editAddress').value = account.address;
            document.getElementById('editRole').value = account.role;

            // Hiển thị modal chỉnh sửa
            const editModal = new bootstrap.Modal(document.getElementById('editModal1'));
            editModal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin tài khoản!');
        });
}

function closeModal() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

function saveChanges() {
    const id = document.getElementById('AccountId').value;
    const username = document.getElementById('editAccountName').value;
    const password = document.getElementById('editPassword').value;
    const fullName = document.getElementById('editFullName').value;
    const phoneNumber = document.getElementById('editPhoneNumber').value;
    const email = document.getElementById('editEmail').value;
    const address = document.getElementById('editAddress').value;
    const role = document.getElementById('editRole').value;

    const updatedData = {
        id,
        username,
        fullName,
        phoneNumber,
        email,
        address,
        role
    };

    fetch(`/admin/user/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Cập nhật thất bại!');
            }
            return response.json();
        })
        .then(data => {
            showAlert('success', 'Lưu thay đổi thành công!');
            const editModal = bootstrap.Modal.getInstance(document.getElementById('editModal1'));
            editModal.hide();
            loadAccounts(); // Cập nhật danh sách tài khoản
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lưu thay đổi!');
        });
}

function clearFormSearch() {
    // Xóa nội dung ô input tìm kiếm
    document.getElementById('searchInput').value = '';

    // Đặt lại các ô select về giá trị mặc định
    document.getElementById('statusFilter').selectedIndex = 0;
}
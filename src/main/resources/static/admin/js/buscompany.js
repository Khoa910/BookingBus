
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

function loadCompany() {
    fetch('/admin-company/company')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ
            data.forEach(company => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td th:text="${company.id}">ID</td>
                        <td th:text="${company.name}">Name</td>
                        <td th:text="${company.phone_number}">PhoneNumber</td>
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" th:attr="data-id=${company.id}" onclick="editAccount(this)">Chỉnh sửa</button>
                        </td>
                    `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

function filterCompany() {
    const searchInput = removeDiacritics(document.getElementById('searchInput').value.toLowerCase().trim());  // Loại bỏ dấu và chuyển thành chữ thường
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const companyName = removeDiacritics(row.cells[1].innerText.toLowerCase());
        const companyPhone = removeDiacritics(row.cells[2].innerText.toLowerCase());

        // Kiểm tra nếu giá trị tìm kiếm có trong một trong các cột (so sánh gần đúng)
        const matchesSearch =
            fuzzySearch(companyName, searchInput) ||
            fuzzySearch(companyPhone, searchInput);

        // Hiển thị hoặc ẩn hàng dựa trên kết quả tìm kiếm
        row.style.display = matchesSearch ? '' : 'none';
    });
}

// Hàm tìm kiếm gần đúng (fuzzy search)
function fuzzySearch(text, searchInput) {
    // Nếu không có tìm kiếm, hiển thị tất cả
    if (searchInput === '') return true;

    // Loại bỏ khoảng trắng và chuyển thành chữ thường
    const normalizedText = text.replace(/\s+/g, '').toLowerCase();
    const normalizedSearchInput = searchInput.replace(/\s+/g, '').toLowerCase();

    // Kiểm tra nếu chuỗi tìm kiếm là một phần của chuỗi tìm thấy
    return normalizedText.includes(normalizedSearchInput);
}

// Hàm loại bỏ dấu tiếng Việt
function removeDiacritics(str) {
    const map = {
        a: 'áàảãạăắằẳẵặâấầẩẫậ',
        e: 'éèẻẽẹêếềểễệ',
        i: 'íìỉĩị',
        o: 'óòỏõọôốồổỗộơớờởỡợ',
        u: 'úùủũụưứừửữự',
        y: 'ýỳỷỹỵ',
        d: 'đ',
    };
    str = str.toLowerCase();
    for (const letter in map) {
        const regex = new RegExp('[' + map[letter] + ']', 'g');
        str = str.replace(regex, letter);
    }
    return str;
}

function showAddModalCompany() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addCompany() {
    // Lấy dữ liệu từ các trường input
    const companyName = document.getElementById('addCompanyName').value;
    const companyPhone = document.getElementById('addPhoneNumber').value;

    // Kiểm tra dữ liệu đầu vào
    if (!companyName || !companyPhone) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm tài khoản này không?");
    if (!confirmation) {
        return; // Nếu người dùng không xác nhận, dừng thao tác
    }

    // Tạo đối tượng dữ liệu tài khoản
    const companyData = {
        companyName,
        companyPhone
    };
    console.log('Dữ liệu gửi:', companyData);

    // Gửi yêu cầu POST tới server
    fetch('/admin-company/company/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // [csrfHeader]: csrfToken // Thêm CSRF token vào header
        },
        body: JSON.stringify(companyData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    showAlert('danger', errorData.message || 'Thêm công ty thất bại!');
                });
            }
            showAlert('success', 'Thêm công ty thành công!');
            loadCompany(); // Tải lại danh sách công ty
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm tài khoản:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm công ty!');
        });
}

function deleteCompany(button) {
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

function editCompany(button) {
    // Lấy accountId từ button
    const companyId = event.target.getAttribute('data-id');
    console.log(companyId);

    // Fetch thông tin tài khoản từ API
    fetch(`/admin/user/${accountId}`)
        .then(response => response.json())
        .then(account => {
            console.log(account);
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

function closeModalCompany() {
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
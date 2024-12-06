
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
    fetch('/admin/user/listUser')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ
            data.forEach(account => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${account.username}</td>
                        <td>${account.password}</td>
                        <td>${account.full_nameA}</td> <!-- Sửa thành full_name -->
                        <td>${account.phone_numberA}</td> <!-- Sửa thành phone_number -->
                        <td>${account.email}</td>
                        <td>${account.address}</td>
                        <td>${account.role_name}</td> <!-- Nếu RoleRespond có thuộc tính 'name' -->
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" th:attr="data-id=${account.id}" onclick="editAccount(this)">Chỉnh sửa</button>
                            <button type="button" class="btn btn-danger btn-sm" th:attr="data-id=${account.id}" onclick="deleteAccount(this)">Xóa</button>
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
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const acc = removeDiacritics(row.cells[0].innerText.toLowerCase());
        const name = removeDiacritics(row.cells[2].innerText.toLowerCase());
        const phone = removeDiacritics(row.cells[3].innerText.toLowerCase());
        const email = removeDiacritics(row.cells[4].innerText.toLowerCase());
        const address = removeDiacritics(row.cells[5].innerText.toLowerCase());
        const role = removeDiacritics(row.cells[6].innerText.toLowerCase());

        // Kiểm tra nếu giá trị tìm kiếm có trong một trong các cột (so sánh gần đúng)
        const matchesSearch =
            fuzzySearch(acc, searchInput) ||
            fuzzySearch(name, searchInput)||
            fuzzySearch(phone, searchInput)||
            fuzzySearch(email, searchInput)||
            fuzzySearch(address, searchInput)||
            fuzzySearch(role, searchInput);

        // Hiển thị hoặc ẩn hàng dựa trên kết quả tìm kiếm
        row.style.display = matchesSearch ? '' : 'none';
    });
}

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

function showAddModal() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addAccount() {
    // Lấy dữ liệu từ các trường input
    const username = document.getElementById('newAccountName').value;
    const password = document.getElementById('newPassword').value;
    const full_name = document.getElementById('newFullName').value;
    const phone_Number = document.getElementById('newPhoneNumber').value;
    const email = document.getElementById('newEmail').value;
    const address = document.getElementById('newAddress').value;
    const role = document.getElementById('newRole').value;

    // Kiểm tra dữ liệu đầu vào
    if (!username || !password || !full_name || !phone_Number || !email || !address || !role) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    if (!isValidEmail(email)) {
        showAlert('danger', 'Vui lòng nhập địa chỉ email hợp lệ!');
        return;
    }

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm tài khoản này không?");
    if (!confirmation) {
        return; // Nếu người dùng không xác nhận, dừng thao tác
    }

    // Tạo đối tượng dữ liệu tài khoản
    const accountData = {
        username,
        password,
        full_name,
        phone_Number,
        email,
        address,
        role
    };

    console.log('Dữ liệu gửi:', accountData);

    // // Lấy CSRF token và header từ thẻ meta
    // const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    // const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    // // console.log('CSRF Header:', csrfHeader);

    // Gửi yêu cầu POST tới server
    fetch('/admin/user/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // [csrfHeader]: csrfToken // Thêm CSRF token vào header
        },
        body: JSON.stringify(accountData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    showAlert('danger', errorData.message || 'Thêm tài khoản thất bại!');
                });
            }
            showAlert('success', 'Thêm tài khoản thành công!');
            loadAccounts(); // Tải lại danh sách tài khoản
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm tài khoản:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm tài khoản!');
        });
}

// Hàm kiểm tra định dạng email
function isValidEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Định dạng email cơ bản
    return regex.test(email);
}

function deleteAccount(button) {
    const accId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin/user/delete/${accId}`, { method: 'DELETE' })
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

function editAccount(button){
    const accountId = button.getAttribute('data-id'); // Sử dụng button thay vì event.target
    const row = button.parentElement.parentElement;
    const cells = row.querySelectorAll('td');
    const account = {
        name: cells[0].innerText,
        pass: cells[1].innerText,
        fullname: cells[2].innerText,
        phone: cells[3].innerText,
        email: cells[4].innerText,
        addressAcc: cells[5].innerText,
        roleAcc: cells[6].innerText
    };

    document.getElementById('AccountId').value = accountId;
    document.getElementById('editAccountName').value = account.name;
    document.getElementById('editPassword').value = ""; // Không hiển thị mật khẩu
    document.getElementById('editFullName').value = account.fullname;
    document.getElementById('editPhoneNumber').value = account.phone;
    document.getElementById('editEmail').value = account.email;
    document.getElementById('editAddress').value = account.addressAcc;
    document.getElementById('editRole').value = account.roleAcc;
    const editModal = new bootstrap.Modal(document.getElementById('editModal1'));
    editModal.show();

}

function closeModal() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

function saveChangesAccount() {
    const id = document.getElementById('AccountId').value;
    console.log(id);
    const username = document.getElementById('editAccountName').value;
    // const password = document.getElementById('editPassword').value;
    const full_name = document.getElementById('editFullName').value;
    const phone_Number = document.getElementById('editPhoneNumber').value;
    const email = document.getElementById('editEmail').value;
    const address = document.getElementById('editAddress').value;
    const role = document.getElementById('editRole').value;

    const updatedData = {
        id,
        username,
        full_name,
        phone_Number,
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

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

            data.forEach(account => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${account.accountId}</td>
                        <td>${account.accountName}</td>
                        <td>${account.password}</td>
                        <td>${account.email}</td>
                        <td>${account.customer.customerId}</td>                
                        <td>
                            <span>${account.status == 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</span>
                        </td>
                        <td>${account.time}</td>
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" data-id="${account.accountId}" onclick="editAccount(this)">Chỉnh sửa</button>
                            <button type="button" class="btn btn-danger btn-sm" data-id="${account.accountId}" ${account.status == 0 ? 'disabled' : ''} onclick="deleteAccount(this)">Xóa</button>
                        </td>
                    `;

                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}
/*
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

            const matchesStatus = statusFilter === '' ||
                (statusFilter === '1' && status === 'Hoạt động') ||
                (statusFilter === '0' && status === 'Ngưng hoạt động');

            if (matchesSearch && matchesStatus) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    }
*/
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


function fetchCustomerName(customerID, isEdit = false) {
    const customerNameInput = isEdit ? document.getElementById('customerName') : document.getElementById('newCustomerName'); // Ô nhập tên khách hàng

    if (customerID) {
        // Sử dụng endpoint của CustomerControllerAdmin để lấy thông tin khách hàng
        fetch(`/admin/customers/${customerID}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 404) {
                    // Nếu không tìm thấy khách hàng, hiển thị thông báo
                    customerNameInput.value = 'Khách hàng không tồn tại';
                    throw new Error('Khách hàng không tồn tại');
                } else {
                    throw new Error('Lỗi mạng: ' + response.status);
                }
            })
            .then(data => {
                // Kiểm tra xem dữ liệu có tồn tại không và có chứa thuộc tính customerName không
                if (data && data.customerName) {
                    // Cập nhật tên khách hàng vào ô tên
                    customerNameInput.value = data.customerName;
                } else {
                    // Nếu không có tên, hiển thị thông báo không tồn tại vào ô nhập
                    customerNameInput.value = 'Khách hàng không tồn tại';
                }
            })
            .catch(error => {
                console.error('Error fetching customer:', error);
                // Nếu có lỗi, cũng có thể hiển thị thông báo không tồn tại vào ô nhập
                customerNameInput.value = 'Lỗi trong việc lấy thông tin khách hàng';
            });
    } else {
        // Nếu không có mã khách hàng, xóa tên
        customerNameInput.value = '';
    }
}





function showAddModal() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addAccount() {
    const accountName = document.getElementById('newAccountName').value;
    const email = document.getElementById('newEmail').value;
    const status = document.getElementById('newStatus').value;
    const customerID = document.getElementById('newCustomerID').value;
    const customerNameInput = document.getElementById('newCustomerName').value;
    const password = document.getElementById('newPassword').value; // Lấy giá trị mật khẩu

    // Kiểm tra dữ liệu đầu vào
    if (!accountName || !email || !customerID || !password) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }
    if (!isValidEmail(email)) {
        showAlert('danger', 'Vui lòng nhập địa chỉ email hợp lệ!');
        return;
    }
    if (customerNameInput === "Khách hàng không tồn tại") {
        showAlert('danger', 'Chưa có thông tin khách hàng');
        return;
    }

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm tài khoản này không?");
    if (!confirmation) {
        // Nếu người dùng không xác nhận, dừng thao tác
        return;
    }

    // Thêm tài khoản
    const accountData = {
        accountName,
        email,
        status,
        customerID,
        password // Thêm mật khẩu vào dữ liệu gửi
    };

    fetch('/admin/accounts/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
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
            loadAccounts();
            clearFormSearch();
            document.getElementById('addForm').reset();
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide();
        })
        .catch(error => {
            console.error('Lỗi khi thêm tài khoản:', error);
        });
}




/*
    function addAccount() {
        const accountName = document.getElementById('newAccountName').value;
        const email = document.getElementById('newEmail').value;
        const status = document.getElementById('newStatus').value;
        const customerID = document.getElementById('newCustomerID').value;
        const customerNameInput = document.getElementById('newCustomerName').value;

        // Kiểm tra dữ liệu đầu vào
        if (!accountName || !email || !customerID) {
            showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
            return;
        }
        if (!isValidEmail(email)) {
            showAlert('danger', 'Vui lòng nhập địa chỉ email hợp lệ!');
            return;
        }

        // Kiểm tra sự tồn tại của customerID
        fetch(`/admin/accounts/checkCustomerID/${customerID}`)
            .then(response => {
                if (!response.ok) return showAlert('danger', 'Lỗi khi kiểm tra customerID');
                return response.text();
            })
            .then(data => {
                if (data !== "CustomerID exists.") {
                    showAlert('warning', 'Khách hàng không tồn tại, nhưng sẽ tiếp tục kiểm tra tài khoản!');
                    return; // Tiếp tục kiểm tra tài khoản
                }
                return fetch(`/admin/accounts/checkAccountByCustomerID/${customerID}`);
            })
            .then(response => {
                if (!response.ok) return showAlert('danger', 'Khách hàng này đã có tài khoản!');
                return response.json();
            })
            .then(data => {
                if (data.length > 0) {
                    showAlert('danger', 'Khách hàng này đã có tài khoản!');
                    return;
                }

                // Thêm tài khoản
                const accountData = {
                    accountName,
                    email,
                    status,
                    customerID
                };
                return fetch('/admin/accounts/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(accountData)
                });
            })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Thêm tài khoản thành công!');
                    loadAccounts();
                    document.getElementById('addForm').reset();
                    const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
                    addModal.hide();
                } else {
                    showAlert('danger', 'Thêm tài khoản thất bại!');
                }
            })
            .catch(error => {
                console.error('Lỗi khi thêm tài khoản:', error);
            });
    }

*/






// Hàm kiểm tra định dạng email
function isValidEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Định dạng email cơ bản
    return regex.test(email);
}


function deleteAccount(button) {
    const accountId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin/accounts/delete/${accountId}`, { method: 'DELETE' })
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
    const accountId = button.getAttribute('data-id');
    fetch(`/admin/accounts/${accountId}`)
        .then(response => response.json())
        .then(account => {
            document.getElementById('accountId').value = account.accountId;
            document.getElementById('accountName').value = account.accountName;
            document.getElementById('email').value = account.email;
            document.getElementById('status').value = account.status;
            document.getElementById('password').value = account.password;

            if (account.customer) {
                document.getElementById('customerID').value = account.customer.customerId;
                fetchCustomerName(account.customer.customerId, true);
            } else {
                document.getElementById('customerID').value = '';
                document.getElementById('customerName').value = '';
            }

            const editModal = new bootstrap.Modal(document.getElementById('editModal'));
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
    const accountId = document.getElementById('accountId').value;
    const accountName = document.getElementById('accountName').value;
    const email = document.getElementById('email').value;
    const status = document.getElementById('status').value;
    const customerID = document.getElementById('customerID').value;
    const customerName = document.getElementById('customerName').value;
    const password = document.getElementById('password').value;

    if (!accountName || !email || !customerID || !password) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }
    if (!isValidEmail(email)) {
        showAlert('danger', 'Vui lòng nhập địa chỉ email hợp lệ!');
        return;
    }
    if (customerName === "Khách hàng không tồn tại") {
        showAlert('danger', 'Chưa có thông tin khách hàng');
        return;
    }
    /*
            // Hiển thị alert xác nhận
            const confirmation = confirm("Bạn có chắc chắn muốn thêm tài khoản này không?");
            if (!confirmation) {
                // Nếu người dùng không xác nhận, dừng thao tác
                return;
            }
    */
    // Hiển thị modal xác nhận
    const confirmSaveButton = document.getElementById('confirmSaveButton');
    confirmSaveButton.onclick = function() {
        fetch(`/admin/accounts/update/${accountId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                accountId: accountId,
                accountName: accountName,
                email: email,
                status: status,
                password: password
            })
        })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Lưu thay đổi thành công!');
                    loadAccounts();
                    clearFormSearch();
                    closeModal(); // Đóng modal sau khi lưu thành công
                } else {
                    showAlert('danger', 'Lưu thay đổi thất bại!');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert('danger', 'Có lỗi xảy ra khi lưu thay đổi!');
            });

        // Đóng modal sau khi xác nhận lưu
        $('#confirmSaveModal').modal('hide');
    };

    // Mở modal xác nhận
    $('#confirmSaveModal').modal('show');
}
function clearFormSearch() {
    // Xóa nội dung ô input tìm kiếm
    document.getElementById('searchInput').value = '';

    // Đặt lại các ô select về giá trị mặc định
    document.getElementById('statusFilter').selectedIndex = 0;
}
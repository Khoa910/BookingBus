
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

function loadStype() {
    fetch('/admin-type/type/listType')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ
            data.forEach(type => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${type.id}</td>
                        <td>${type.SeatCount}</td>
                        <td>${type.describe}</td>
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" data-id="${type.id}" onclick="editCompany(this)">Chỉnh sửa</button>
                            <button type="button" class="btn btn-danger btn-sm" data-id="${type.id}" onclick="deleteCompany(this)">Xóa</button>
                        </td>
                    `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

function filterStype() {
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

function showAddModalStype() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addStype() {
    // Lấy dữ liệu từ các trường input
    const count = document.getElementById('addSeatCount').value.trim();
    const des = document.getElementById('addDescription').value;

    console.log(count);
    console.log(des);

    // Kiểm tra dữ liệu đầu vào
    if (!count || isNaN(count) || !des) {  // Ensure 'count' is a valid number
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin hợp lệ!');
        return;
    }

    // Chuyển 'count' thành số (Long type)
    // const countLong = parseInt(count, 10);  // Parse 'count' to an integer

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm công ty này không?");
    if (!confirmation) {
        return; // Nếu người dùng không xác nhận, dừng thao tác
    }

    // Tạo đối tượng dữ liệu tài khoản
    const typeData = {
        count,  // Send 'count' as a number
        des
    };
    console.log('Dữ liệu gửi:', typeData);

    // Gửi yêu cầu POST tới server
    fetch('/admin-type/type/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // [csrfHeader]: csrfToken // Thêm CSRF token vào header
        },
        body: JSON.stringify(typeData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    showAlert('danger', errorData.message || 'Thêm công ty thất bại!');
                });
            }
            showAlert('success', 'Thêm công ty thành công!');
            loadStype(); // Tải lại danh sách công ty
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm công ty:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm công ty!');
        });
}


function deleteStype(button) {
    const stypeId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin-type/type/delete/${stypeId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa loại ghế thành công!');
                    // Disable the delete button instead of removing the row
                    loadStype();
                    clearFormSearch();

                } else {
                    showAlert('danger', 'Xóa loại ghế thất bại!');
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

function editStype(button) {
    const typeId = button.getAttribute('data-id'); // Sử dụng button thay vì event.target
    const row = button.parentElement.parentElement;
    const cells = row.querySelectorAll('td');
    const type = {
        count: cells[1].innerText,
        des: cells[2].innerText,
    };

    document.getElementById('stypeId').value = typeId;
    document.getElementById('editSeatCount').value = type.count;
    document.getElementById('editDescription').value = type.des;
    const editModal = new bootstrap.Modal(document.getElementById('editModal1'));
    editModal.show();
}

function closeModalStype() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

function saveChangesStype() {
    const id = document.getElementById('stypeId').value;
    const count = document.getElementById('editSeatCount').value;
    const des = document.getElementById('editDescription').value;

    const updatedData = {
        id,
        count,
        des,
    };

    fetch(`/admin-type/type/update/${id}`, {
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
            loadStype(); // Cập nhật danh sách tài khoản
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
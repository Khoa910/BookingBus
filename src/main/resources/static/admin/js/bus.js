
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

function loadBuss() {
    fetch('/admin-bus/bus/listBus')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ
            data.forEach(buss => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${buss.id}</td>
                        <td>${buss.PlateBus}</td>
                        <td>${buss.SeatType}</td>
                        <td>${buss.BusType}</td>
                        <td>${buss.BCompany}</td>
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" th:attr="data-id=${buss.id}" onclick="editBuss(this)">Chỉnh sửa</button>
                            <button type="button" class="btn btn-danger btn-sm" th:attr="data-id=${buss.id}" onclick="deleteBuss(this)">Xóa</button>
                        </td>
                    `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

function filterBuss() {
    const searchInput = removeDiacritics(document.getElementById('searchInput').value.toLowerCase().trim());  // Loại bỏ dấu và chuyển thành chữ thường
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const PlateBuss = removeDiacritics(row.cells[1].innerText.toLowerCase());
        const companyName = removeDiacritics(row.cells[4].innerText.toLowerCase());

        // Kiểm tra nếu giá trị tìm kiếm có trong một trong các cột (so sánh gần đúng)
        const matchesSearch =
            fuzzySearch(PlateBuss, searchInput) ||
            fuzzySearch(companyName, searchInput);

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

function showAddModalBuss() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addBuss() {
    // Lấy dữ liệu từ các trường input
    const plate = document.getElementById('newBusPlate').value;
    const selectedSeatId = document.getElementById('newSeatCount').value;
    const selectedBusType = document.getElementById('newBusType').value;
    const selectedCompanyId = document.getElementById('newCompany').value;

    // Kiểm tra dữ liệu đầu vào
    if (!plate) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Hiển thị alert xác nhận
    const confirmation = confirm("Bạn có chắc chắn muốn thêm công ty này không?");
    if (!confirmation) {
        return; // Nếu người dùng không xác nhận, dừng thao tác
    }

    // Tạo đối tượng dữ liệu tài khoản
    const bussData = {
        plate,
        selectedSeatId,
        selectedBusType,
        selectedCompanyId
    };
    console.log('Dữ liệu gửi:', bussData);

    // Gửi yêu cầu POST tới server
    fetch('/admin-bus/bus/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // [csrfHeader]: csrfToken // Thêm CSRF token vào header
        },
        body: JSON.stringify(bussData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    showAlert('danger', errorData.message || 'Thêm xe thất bại!');
                });
            }
            showAlert('success', 'Thêm xe thành công!');
            loadBuss(); // Tải lại danh sách công ty
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm xe:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm xe!');
        });
}

function deleteBuss(button) {
    const busId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin-bus/bus/delete/${busId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa công ty thành công!');
                    // Disable the delete button instead of removing the row
                    loadBuss();
                    clearFormSearch();

                } else {
                    showAlert('danger', 'Xóa công ty thất bại!');
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

function editBuss(button) {
    const bussId = button.getAttribute('data-id'); // Sử dụng button thay vì event.target
    const row = button.parentElement.parentElement;
    const cells = row.querySelectorAll('td');
    const buss = {
        Bplate: cells[1].innerText,
        Bseat: cells[2].innerText,
        Btypeseat: cells[3].innerText,
        Bcompany: cells[4].innerText,
    };

    document.getElementById('BusId').value = bussId;
    document.getElementById('editBusPlate').value = buss.Bplate;
    document.getElementById('editBusType').value = buss.Btypeseat;
    // Gán text vào dropdown của "Số chỗ"
    const seatOptions = document.getElementById('editSeatCount').options;
    for (let i = 0; i < seatOptions.length; i++) {
        if (seatOptions[i].innerText.trim() === buss.Bseat.trim()) {
            seatOptions[i].selected = true;
            break;
        }
    }

    // Gán giá trị vào dropdown "Loại ghế"
    document.getElementById('editBusType').value = buss.Btypeseat;

    // Gán text vào dropdown "Công ty"
    const companyOptions = document.getElementById('editCompany').options;
    for (let i = 0; i < companyOptions.length; i++) {
        if (companyOptions[i].innerText.trim() === buss.Bcompany.trim()) {
            companyOptions[i].selected = true;
            break;
        }
    }
    const editModal = new bootstrap.Modal(document.getElementById('editModal1'));
    editModal.show();
}

function closeModalBuss() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

function saveChangesBuss() {
    const id = document.getElementById('BusId').value;
    const plate = document.getElementById('editBusPlate').value;
    const selectedSeatId = document.getElementById('editSeatCount').value;
    const selectedBusType = document.getElementById('editBusType').value;
    const selectedCompanyId = document.getElementById('editCompany').value;

    console.log(plate);
    console.log(selectedSeatId);
    console.log(selectedBusType);
    console.log(selectedCompanyId);

    const updatedData = {
        id,
        plate,
        selectedSeatId,
        selectedBusType,
        selectedCompanyId
    };

    fetch(`/admin-bus/bus/update/${id}`, {
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
            loadBuss(); // Cập nhật danh sách xe
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
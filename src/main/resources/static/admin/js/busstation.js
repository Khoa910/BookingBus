
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

function loadStations() {
    fetch('/admin-station/station/listStation', {
        method: 'GET', // Sử dụng GET
        headers: {
            'Content-Type': 'application/json', // Định dạng dữ liệu là JSON
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // const values = Object.values(data);
            // console.log(values);
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(station => {
            // Duyệt qua từng key trong đối tượng
            // Object.keys(data).forEach(key => {
            //     const station = data[key]; // Lấy từng đối tượng station
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${station.id}</td>
                        <td>${station.name}</td> 
                        <td>${station.address}</td> 
                        <td class="d-flex justify-content-evenly">
                            <button type="button" class="btn btn-warning btn-sm" data-id="${station.id}" onclick="editAccount(this)">Chỉnh sửa</button>
                            <button type="button" class="btn btn-danger btn-sm" data-id="${station.id}" onclick="deleteStation(this)">Xóa</button>
                        </td>
                    `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

function filterStation() {
    const searchInput = removeDiacritics(document.getElementById('searchInput').value.toLowerCase().trim());  // Loại bỏ dấu và chuyển thành chữ thường
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const stationName = removeDiacritics(row.cells[1].innerText.toLowerCase());
        const stationAddress = removeDiacritics(row.cells[2].innerText.toLowerCase());

        // Kiểm tra nếu giá trị tìm kiếm có trong một trong các cột (so sánh gần đúng)
        const matchesSearch =
            fuzzySearch(stationName, searchInput) ||
            fuzzySearch(stationAddress, searchInput);

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

function showAddModalStation() {
    // Làm sạch các trường trong form
    document.getElementById('addForm').reset(); // Giả sử form của bạn có id là 'accountForm'

    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addStation() {
    // Lấy dữ liệu từ các trường input
    const nameStation = document.getElementById('newStationName').value;
    const addressStation = document.getElementById('newStationAddress').value;

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
            loadStations(); // Tải lại danh sách trạm xe
            document.getElementById('addForm').reset(); // Reset form
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide(); // Ẩn modal sau khi thêm thành công
        })
        .catch(error => {
            console.error('Lỗi khi thêm trạm xe:', error);
            showAlert('danger', 'Có lỗi xảy ra khi thêm trạm xe!');
        });
}

function deleteStation(button) {
    const stationId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin-station/station/delete/${stationId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa tài khoản thành công!');
                    // Disable the delete button instead of removing the row
                    loadStations();
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

function editStation(button) {
    const stationId = button.getAttribute('data-id'); // Sử dụng button thay vì event.target
    const row = button.parentElement.parentElement;
    const cells = row.querySelectorAll('td');
    const station = {
        name: cells[1].innerText,
        address: cells[2].innerText,
    };

    document.getElementById('StationId').value = stationId;
    document.getElementById('editStationName').value = station.name;
    document.getElementById('editStationAddress').value = station.address;
    const editModal = new bootstrap.Modal(document.getElementById('editModal1'));
    editModal.show();
}

function closeModalStation() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

function saveChangesStation() {
    const id = document.getElementById('StationId').value;
    const name = document.getElementById('editStationName').value;
    const address = document.getElementById('editStationAddress').value;

    const updatedData = {
        id,
        name,
        address
    };

    fetch(`/admin-station/station/update/${id}`, {
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
            loadStations(); // Cập nhật danh sách trạm xe
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
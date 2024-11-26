document.addEventListener("DOMContentLoaded", function () {
    let tourList = [];
    let categoryList = [];

    function fetchData() {
        return Promise.all([fetchCategories(), fetchTours()]);
    }

    function fetchCategories() {
        return fetch('/admin/tours/categories')
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(categories => {
                categoryList = categories; // Lưu danh sách categories
                // Cập nhật cho cả hai select
                populateCategorySelect(categories, "tourCategory"); // Đối với form thêm tour
                populateCategorySelect(categories, "categorySelect"); // Đối với form lọc tour
            })
            .catch(error => {
                console.error('Error fetching categories:', error);
                showAlert('danger', 'Không thể tải danh mục.'); // Hiển thị thông báo lỗi
            });
    }


    function populateCategorySelect(categories, selectElementId) {
        const categorySelect = document.getElementById(selectElementId);
        categorySelect.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "Chọn Loại Tour";
        categorySelect.appendChild(defaultOption);

        categories.forEach(category => {
            const option = document.createElement("option");
            option.value = category.categoryId;
            option.textContent = category.categoryName;
            categorySelect.appendChild(option);
        });
    }


    function fetchTours() {
        return fetch('/admin/tours/list-tour')
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(tours => {
                tourList = tours;
                renderTours();
            })
            .catch(error => {
                console.error('Error fetching tours:', error);
                showAlert('danger', 'Không thể tải tour.'); // Hiển thị thông báo lỗi
            });
    }

    // Hàm để hiển thị thông báo với hiệu ứng
    function showAlert(type, message) {
        const alertContainer = document.getElementById('alert-container');
        if (alertContainer) {
            const alertBox = document.createElement('div');
            alertBox.className = `alert alert-${type}`;
            alertBox.innerText = message;
            alertContainer.appendChild(alertBox);
            alertBox.style.display = 'block';
            alertBox.style.opacity = 1; // Bắt đầu với độ mờ đầy đủ

            // Thêm hiệu ứng mờ dần và xóa alert sau 3 giây
            setTimeout(() => {
                alertBox.style.opacity = 0; // Bắt đầu mờ dần
                setTimeout(() => {
                    alertBox.remove(); // Xóa alert khỏi DOM sau khi đã mờ
                }, 600); // Thời gian để mờ
            }, 3000); // Thời gian để hiển thị alert
        } else {
            console.error('Phần tử alert-container không tồn tại.');
        }
    }

    function renderTours() {
        const tourTableBody = document.getElementById("tourTableBody");
        tourTableBody.innerHTML = '';

        if (tourList.length === 0) {
            tourTableBody.innerHTML = '<tr><td colspan="8">Không có tour nào được tìm thấy.</td></tr>';
            return;
        }

        tourList.forEach(tour => {
            const row = document.createElement("tr");
            row.innerHTML = `
		        <td>${tour.tourId}</td>
		        <td>${tour.tourName}</td>
		        <td>${tour.tourDetail ? tour.tourDetail : 'Không có chi tiết'}</td>
		        <td>${tour.status === 1 ? 'Kích hoạt' : 'Ngưng hoạt động'}</td>
		        <td>${tour.tourCode}</td>
		        <td>${tour.dayStay}</td>
		        <td data-category-id="${tour.category ? tour.category.categoryId : ''}">
		            ${tour.category ? tour.category.categoryName : 'N/A'}
		        </td>
		        <td>
				<div class="text-center">
				    <div class="row mb-2">
				        <!-- Ô 1: Tour Time -->
				        <div class="col-6">
				            <button class="btnadd-tour-time btn btn-info btn-sm w-100" onclick="showTourTimes(${tour.tourId})">Lịch Tour</button>
				        </div>
				        <!-- Ô 2: Ảnh Tour -->
				        <div class="col-6">
				            <button class="btn btn-primary btn-sm w-100" onclick="viewTourImage(${tour.tourId})">
				                <i class="fas fa-image"></i> Ảnh
				            </button>
				        </div>
				    </div>

				    <div class="row mb-2">
				        <!-- Ô 3: Sửa -->
				        <div class="col-6">
				            <button data-id="${tour.tourId}" class="edit-btn btn btn-warning btn-sm w-100">Sửa</button>
				        </div>
				        <!-- Ô 4: Xóa -->
				        <div class="col-6">
				            <button class="delete-btn btn btn-danger btn-sm w-100" data-id="${tour.tourId}" ${tour.status == 0 ? 'disabled' : ''}>Xóa</button>
				        </div>
				    </div>
				</div>



		        </td>
		    `;
            tourTableBody.appendChild(row);
        });

        // Gán sự kiện lắng nghe cho các nút xóa
        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', function() {
                deleteTour(this);
            });
        });

        // Gán sự kiện lắng nghe cho các nút sửa
        document.querySelectorAll('.edit-btn').forEach(button => {
            button.addEventListener('click', function() {
                showEditModal(this);
            });
        });
    }
    function viewTourImage(tourId) {
        // Gọi API để lấy hình ảnh từ backend
        fetch(`/api/tour-images/${tourId}`)
            .then(response => response.json())  // Chuyển đổi phản hồi thành JSON
            .then(images => {
                // In ra dữ liệu JSON
                console.log('Images Data:', images);

                // Lấy đối tượng gallery để chèn hình ảnh
                let gallery = document.getElementById('tourImageGallery');
                gallery.innerHTML = '';  // Xóa các hình ảnh cũ trước khi thêm mới

                // Duyệt qua danh sách hình ảnh và thêm vào modal
                images.forEach(image => {
                    // Tạo thẻ img cho mỗi hình ảnh
                    let imgElement = document.createElement('img');
                    imgElement.src = image.imageUrl;  // Đường dẫn hình ảnh
                    imgElement.alt = `Hình ảnh tour ${tourId}`;
                    imgElement.classList.add('img-fluid', 'mb-2');  // Thêm các lớp để hình ảnh hiển thị đẹp

                    // Thêm thẻ img vào gallery
                    gallery.appendChild(imgElement);
                });

                // Hiển thị modal
                $('#tourImageModal').modal('show');
            })
            .catch(error => {
                console.error('Error fetching tour images:', error);
            });
    }



    function filterTours() {
        const searchInput = document.getElementById('searchInput').value.toLowerCase();
        const categoryFilter = document.getElementById('categorySelect').textContent;
        const statusFilter = document.getElementById('statusSelect').value;
        const tableRows = document.querySelectorAll('#tourTableBody tr');
        tableRows.forEach(row => {
            const tourID = row.cells[0].innerText.toLowerCase();        // Cột ID Tour
            const tourName = row.cells[1].innerText.toLowerCase();      // Cột Tên Tour
            const tourCode = row.cells[4].innerText.toLowerCase();      // Cột Mã Tour
            const status = row.cells[3].innerText.toLowerCase().trim();  // Cột Trạng Thái
            const tourCategory = row.cells[6].innerText.toLowerCase().trim();   // Cột Loại Tour

            // Kiểm tra nếu searchInput có trong tourID, tourName hoặc tourCode
            const matchesSearch =
                tourID.includes(searchInput) ||
                tourName.includes(searchInput) ||
                tourCode.includes(searchInput);

            // Kiểm tra loại tour
            const matchesCategory =
                categoryFilter === '' || // Nếu loại tour là Tất cả
                tourCategory === categoryFilter.toLowerCase();

            // Kiểm tra trạng thái
            const matchesStatus =
                statusFilter === '' || // Nếu trạng thái là Tất cả
                (statusFilter === '1' && status === 'kích hoạt') ||
                (statusFilter === '0' && status === 'ngưng hoạt động');

            // Nếu ô tìm kiếm không trống và không khớp với các điều kiện
            if (searchInput && matchesSearch) {
                row.style.display = matchesCategory && matchesStatus ? '' : 'none'; // Hiển thị hoặc ẩn dựa trên điều kiện
            } else if (!searchInput) {
                // Nếu ô tìm kiếm trống, chỉ cần kiểm tra loại tour và trạng thái
                row.style.display = matchesCategory && matchesStatus ? '' : 'none';
            } else {
                row.style.display = 'none'; // Ẩn hàng nếu không khớp với tìm kiếm
            }
        });
    }


    function deleteTour(button) {
        const tourId = button.getAttribute('data-id'); // Lấy tour ID từ thuộc tính dữ liệu
        const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

        // Thiết lập hành động cho nút xác nhận xóa
        document.getElementById('confirmDeleteButton').onclick = function() {
            fetch(`/admin/tours/delete-tour/${tourId}`, { method: 'DELETE' })
                .then(response => {
                    if (response.ok) {
                        showAlert('success', 'Xóa tour thành công!');

                        // Gọi lại fetchData() để tải lại danh sách mới nhất
                        fetchTours();
                        clearFormSearch();
                        confirmDeleteModal.hide(); // Ẩn modal
                    } else {
                        return response.text().then(text => {
                            showAlert('danger', text || 'Xóa tour thất bại!'); // Hiển thị thông báo lỗi từ server
                        });
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showAlert('danger', 'Có lỗi xảy ra!'); // Hiển thị thông báo lỗi
                });
        };

        confirmDeleteModal.show(); // Hiện modal xác nhận
    }

    // Hàm này sẽ điền danh mục vào select của form edit
    function populateEditCategorySelect(categories, selectedCategoryId) {
        const categorySelect = document.getElementById("editTourCategory");
        categorySelect.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "Chọn Loại Tour";
        categorySelect.appendChild(defaultOption);

        categories.forEach(category => {
            const option = document.createElement("option");
            option.value = category.categoryId;
            option.textContent = category.categoryName;

            // Đặt category đã chọn nếu nó trùng với selectedCategoryId
            if (category.categoryId === selectedCategoryId) {
                option.selected = true;
            }

            categorySelect.appendChild(option);
        });
    }

    // Hàm showEditModal, trong đó sẽ gọi populateEditCategorySelect
    function showEditModal(button) {
        const tourId = button.getAttribute('data-id');

        fetch(`/admin/tours/${tourId}`)
            .then(response => response.json())
            .then(tour => {
                // Điền dữ liệu vào các input trong form edit
                document.getElementById('editTourId').value = tourId;
                document.getElementById('editTourName').value = tour.tourName;
                document.getElementById('editTourDetail').value = tour.tourDetail;
                document.getElementById('editTourStatus').value = tour.status;
                document.getElementById('editTourCode').value = tour.tourCode;
                document.getElementById('editDayStay').value = tour.dayStay;

                // Gọi hàm populateEditCategorySelect với danh sách category và id của category hiện tại
                populateEditCategorySelect(categoryList, tour.category ? tour.category.categoryId : '');

                // Sử dụng đúng ID modal
                const editModal = new bootstrap.Modal(document.getElementById('editTourModal'));
                editModal.show();
            })
            .catch(error => {
                console.error('Error fetching tour:', error);
                showAlert('danger', 'Không thể tải thông tin tour!');
            });
    }
    document.getElementById("editTourForm").onsubmit = function (e) {
        e.preventDefault();

        const tourId = parseInt(document.getElementById("editTourId").value, 10);
        const tourName = document.getElementById("editTourName").value.trim();
        const tourDetail = document.getElementById("editTourDetail").value.trim();
        const tourCategoryId = parseInt(document.getElementById("editTourCategory").value, 10); // Đảm bảo là số
        const tourStatus = parseInt(document.getElementById("editTourStatus").value, 10); // Đảm bảo là số
        const tourCode = document.getElementById("editTourCode").value.trim();
        const dayStay = document.getElementById("editDayStay").value.trim();

        // Kiểm tra các trường rỗng
        if (!tourName) {
            showAlert('danger', 'Tên tour không được để trống.');
            return;
        }
        if (!tourDetail) {
            showAlert('danger', 'Chi tiết tour không được để trống.');
            return;
        }
        if (isNaN(tourCategoryId) || tourCategoryId <= 0) {
            showAlert('danger', 'Vui lòng chọn danh mục tour hợp lệ.');
            return;
        }
        if (isNaN(tourStatus)) {
            showAlert('danger', 'Vui lòng chọn trạng thái tour hợp lệ.');
            return;
        }
        if (!tourCode) {
            showAlert('danger', 'Mã tour không được để trống.');
            return;
        }
        if (!dayStay) {
            showAlert('danger', 'Số ngày lưu trú không được để trống.');
            return;
        }

        const tourData = {
            "tourId": tourId,
            "tourName": tourName,
            "tourDetail": tourDetail,
            "category": tourCategoryId,
            "status": tourStatus,
            "tourCode": tourCode,
            "dayStay": dayStay
        };
        console.log(tourData);
        fetch(`/admin/tours/update-tour`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tourData) // Đảm bảo rằng bạn gửi dữ liệu ở dạng JSON
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                showAlert('success', data.message); // Duyệt thông điệp phản hồi từ máy chủ
                $('#editTourModal').modal('hide'); // Đóng modal
                fetchTours(); // Tải lại danh sách tour
                clearFormSearch();

            })
            .catch(error => {
                console.error('Error updating tour:', error);
                showAlert('danger', 'Có lỗi xảy ra. Vui lòng thử lại.'); // Hiển thị thông báo lỗi
            });
    };


    document.querySelector('.add-tour-btn .btn-success').addEventListener('click', function () {
        $('#addTourModal').modal('show');
        document.getElementById("addTourForm").reset();
    });

    document.getElementById("addTourForm").onsubmit = function (e) {
        e.preventDefault();

        const tourName = document.getElementById("tourName").value.trim();
        const tourDetail = document.getElementById("tourDetail").value.trim();
        const tourCategoryId = parseInt(document.getElementById("tourCategory").value, 10); // Đảm bảo là số
        const tourStatus = parseInt(document.getElementById("tourStatus").value, 10); // Đảm bảo là số
        const tourCode = document.getElementById("tourCode").value.trim();
        const dayStay = document.getElementById("dayStay").value.trim();

        // Kiểm tra các trường rỗng
        if (!tourName) {
            showAlert('danger', 'Tên tour không được để trống.');
            return;
        }
        if (!tourDetail) {
            showAlert('danger', 'Chi tiết tour không được để trống.');
            return;
        }
        if (isNaN(tourCategoryId) || tourCategoryId <= 0) {
            showAlert('danger', 'Vui lòng chọn danh mục tour hợp lệ.');
            return;
        }
        if (isNaN(tourStatus)) {
            showAlert('danger', 'Vui lòng chọn trạng thái tour hợp lệ.');
            return;
        }
        if (!tourCode) {
            showAlert('danger', 'Mã tour không được để trống.');
            return;
        }
        if (!dayStay) {
            showAlert('danger', 'Số ngày lưu trú không được để trống.');
            return;
        }

        const tourData = {
            tourName: tourName,
            tourDetail: tourDetail,
            category: tourCategoryId, // Chắc chắn là số
            status: tourStatus, // Chắc chắn là số
            tourCode: tourCode,
            dayStay: dayStay
        };

        fetch('/admin/tours/add-tour', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tourData) // Đảm bảo rằng bạn gửi dữ liệu ở dạng JSON
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                showAlert('success', data.message); // Duyệt thông điệp phản hồi từ máy chủ
                $('#addTourModal').modal('hide'); // Đóng modal
                fetchTours(); // Tải lại danh sách tour
                clearFormSearch();
            })
            .catch(error => {
                console.error('Error adding tour:', error);
                showAlert('danger', 'Có lỗi xảy ra. Vui lòng thử lại.'); // Hiển thị thông báo lỗi
            });
    };

    function clearFormSearch() {
        // Xóa nội dung ô input tìm kiếm
        document.getElementById('searchInput').value = '';

        // Đặt lại các ô select về giá trị mặc định
        document.getElementById('statusSelect').selectedIndex = 0;
        document.getElementById('categorySelect').selectedIndex = 0;
    }

    document.getElementById("clearTourForm").addEventListener("click", function() {
        document.getElementById("addTourForm").reset();
    });

    fetchData(); // Gọi hàm để tải dữ liệu ban đầu
});
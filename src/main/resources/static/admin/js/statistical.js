// let monthlyPaymentChart, dailyPaymentChart;
//
// // Fetch dữ liệu từ API
// fetch('/payment-statistics-data')
//     .then(response => response.json())
//     .then(data => {
//         const monthlyPayments = data.monthlyPayments;
//         const dailyPayments = data.dailyPayments;
//         console.log(data.monthlyPayments);
//
//         // Dữ liệu biểu đồ theo tháng
//         const monthlyPaymentData = {
//             labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
//             datasets: [{
//                 label: 'Thanh toán (VND)',
//                 data: monthlyPayments,
//                 backgroundColor: 'rgba(54, 162, 235, 0.6)',
//                 borderColor: 'rgba(54, 162, 235, 1)',
//                 borderWidth: 1
//             }]
//         };
//
//         // Dữ liệu biểu đồ theo ngày
//         const dailyPaymentData = {
//             labels: Array.from({ length: 31 }, (_, i) => `Ngày ${i + 1}`),
//             datasets: [{
//                 label: 'Thanh toán (VND)',
//                 data: dailyPayments,
//                 backgroundColor: 'rgba(255, 99, 132, 0.6)',
//                 borderColor: 'rgba(255, 99, 132, 1)',
//                 borderWidth: 1
//             }]
//         };
//
//         // Tạo biểu đồ theo tháng
//         const ctx1 = document.getElementById('monthlyPaymentChart').getContext('2d');
//         monthlyPaymentChart = new Chart(ctx1, {
//             type: 'bar',
//             data: monthlyPaymentData,
//             options: {
//                 responsive: true,
//                 scales: {
//                     y: {
//                         beginAtZero: true
//                     }
//                 }
//             }
//         });
//
//         // Tạo biểu đồ theo ngày
//         const ctx2 = document.getElementById('dailyPaymentChart').getContext('2d');
//         dailyPaymentChart = new Chart(ctx2, {
//             type: 'bar',
//             data: dailyPaymentData,
//             options: {
//                 responsive: true,
//                 scales: {
//                     y: {
//                         beginAtZero: true
//                     }
//                 }
//             }
//         });
//     })
//     .catch(error => {
//         console.error('Error fetching payment data:', error);
//     });
//
// // Xử lý thay đổi ComboBox
// document.getElementById('viewType').addEventListener('change', function () {
//     const viewType = this.value;
//
//     if (viewType === 'monthly') {
//         document.getElementById('monthlyChartContainer').style.display = 'block';
//         document.getElementById('dailyChartContainer').style.display = 'none';
//     } else if (viewType === 'daily') {
//         document.getElementById('monthlyChartContainer').style.display = 'none';
//         document.getElementById('dailyChartContainer').style.display = 'block';
//     }
// });
//
// document.getElementById('viewChart').addEventListener('click', function () {
//     const viewType = document.getElementById('viewType').value;
//     const chartImage = document.getElementById('chartImage');
//     chartImage.src = `/api/payment-chart?viewType=${viewType}`;
// });

// let paymentChart;
//
// // Hàm lấy dữ liệu từ API
// async function fetchPaymentData() {
//     try {
//         const response = await fetch('/payment-statistics-data');
//         if (!response.ok) {
//             throw new Error('Lỗi khi lấy dữ liệu từ server');
//         }
//         return await response.json();
//     } catch (error) {
//         console.error(error);
//         alert('Không thể tải dữ liệu. Vui lòng thử lại sau.');
//     }
// }
//
// // Hàm khởi tạo hoặc cập nhật biểu đồ
// function updateChart(viewType, data) {
//     const labels = viewType === 'monthly'
//         ? ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12']
//         : Array.from({ length: 31 }, (_, i) => `Ngày ${i + 1}`);
//
//     const dataset = {
//         labels: labels,
//         datasets: [{
//             label: 'Thanh toán (VND)',
//             data: viewType === 'monthly' ? data.monthlyPayments : data.dailyPayments,
//             backgroundColor: 'rgba(75, 192, 192, 0.6)',
//             borderColor: 'rgba(75, 192, 192, 1)',
//             borderWidth: 1
//         }]
//     };
//
//     const config = {
//         type: 'bar',
//         data: dataset,
//         options: {
//             responsive: true,
//             scales: {
//                 y: {
//                     beginAtZero: true
//                 }
//             }
//         }
//     };
//
//     if (paymentChart) {
//         paymentChart.destroy(); // Hủy biểu đồ cũ trước khi tạo mới
//     }
//
//     const ctx = document.getElementById('paymentChart').getContext('2d');
//     paymentChart = new Chart(ctx, config);
// }
//
// // // Khi thay đổi loại thống kê
// // document.getElementById('viewType').addEventListener('change', async function () {
// //     const viewType = this.value;
// //     const data = await fetchPaymentData();
// //     if (data) {
// //         updateChart(viewType, data);
// //     }
// // });
// document.addEventListener('DOMContentLoaded', function() {
//     document.getElementById('viewType').addEventListener('change', async function () {
//         const viewType = this.value;
//         const data = await fetchPaymentData();
//         if (data) {
//             updateChart(viewType, data);
//         }
//     });
// });
//
//
// // Khởi tạo biểu đồ lần đầu
// (async function initializeChart() {
//     const viewType = document.getElementById('viewType').value;
//     const data = await fetchPaymentData();
//     if (data) {
//         updateChart(viewType, data);
//     }
// })();


// $(document).ready(function () {
//     function fetchData(filter) {
//         $.get('/payment-statistics-data', function (data) {
//             let stats = filter === 'monthly' ? data.monthlyPayments : data.dailyPayments;
//             updateTable(stats, filter);
//         });
//     }
//
//     function updateTable(stats, filter) {
//         const tableBody = $('#statisticsTable tbody');
//         tableBody.empty();
//
//         const isMonthly = filter === 'monthly';
//         stats.forEach((stat, index) => {
//             tableBody.append(`
//                         <tr>
//                             <td>${isMonthly ? 'Month ' + (index + 1) : 'Day ' + (index + 1)}</td>
//                             <td>${stat}</td>
//                         </tr>
//                     `);
//         });
//     }
//
//     // Khởi tạo bảng với thống kê theo tháng
//     fetchData('monthly');
//
//     // Thay đổi hiển thị khi người dùng chọn combo box
//     $('#filter').on('change', function () {
//         const filter = $(this).val();
//         fetchData(filter);
//     });
// });


$(document).ready(function () {
    // Hàm fetch dữ liệu từ API
    function fetchData(filter) {
        $.get('/payment-statistics-data', function (data) {
            const stats = filter === 'monthly' ? data.monthlyPayments : data.dailyPayments;
            updateTable(stats, filter);
        });
    }

    // Hàm cập nhật bảng theo dữ liệu
    function updateTable(stats, filter) {
        const tableBody = $('#statisticsTable tbody');
        tableBody.empty();

        const isMonthly = filter === 'monthly';
        stats.forEach((stat, index) => {
            const period = isMonthly ? `Month ${index + 1}` : `Day ${index + 1}`;
            tableBody.append(`
                        <tr>
                            <td>${period}</td>
                            <td>${stat}</td>
                        </tr>
                    `);
        });
    }

    // Khởi tạo bảng với thống kê theo tháng
    fetchData('monthly');

    // Thay đổi hiển thị khi người dùng chọn từ combo box
    $('#filter').on('change', function () {
        const filter = $(this).val();
        fetchData(filter);
    });
});
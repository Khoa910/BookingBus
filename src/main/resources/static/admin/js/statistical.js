$(document).ready(function () {
    const ctx = document.getElementById('statisticsChart').getContext('2d');
    let chart;

    function createChart(labels, data, label) {
        if (chart) {
            chart.destroy(); // Hủy biểu đồ cũ nếu đã tồn tại
        }
        chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: label,
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Thời gian'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Doanh thu (VNĐ)'
                        }
                    }
                }
            }
        });
    }

    function fetchData(filter) {
        $.get('/admin-payment/payment-statistics-data', function (data) {
            let stats;
            let labels;
            let labelText;

            if (filter === 'daily') {
                stats = data.dailyPayments;
                labels = Object.keys(stats); // Ngày: yyyy-MM-dd
                labelText = 'Doanh thu theo ngày';
            } else if (filter === 'monthly') {
                stats = data.monthlyPayments;
                labels = Object.keys(stats); // Tháng: yyyy-MM
                labelText = 'Doanh thu theo tháng';
            } else if (filter === 'yearly') {
                stats = data.yearlyPayments;
                labels = Object.keys(stats); // Năm: yyyy
                labelText = 'Doanh thu theo năm';
            }

            const values = Object.values(stats); // Giá trị doanh thu
            createChart(labels, values, labelText);
        });
    }

    // Mặc định hiển thị theo ngày
    fetchData('daily');

    // Thay đổi chế độ hiển thị
    $('#filter').on('change', function () {
        const filter = $(this).val();
        fetchData(filter);
    });
});
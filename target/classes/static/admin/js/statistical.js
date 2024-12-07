$(document).ready(function () {
    const ctx = document.getElementById('statisticsChart').getContext('2d');
    let chart;

    function createChart(labels, data) {
        if (chart) {
            chart.destroy(); // Hủy biểu đồ cũ nếu đã tồn tại
        }
        chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Tổng doanh thu',
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
                            text: 'Doanh thu'
                        }
                    }
                }
            }
        });
    }

    function fetchData(filter) {
        $.get('/admin-payment/payment-statistics-data', function (data) {
            const stats = filter === 'monthly' ? data.monthlyPayments : data.dailyPayments;
            const labels = stats.map((_, index) => filter === 'monthly' ? `Tháng ${index + 1}` : `Ngày ${index + 1}`);
            const values = stats.map(stat => stat);
            createChart(labels, values);
        });
    }

    fetchData('monthly');
    $('#filter').on('change', function () {
        const filter = $(this).val();
        fetchData(filter);
    });
});
$(document).ready(function () {
    function fetchData(filter) {
        $.get('/admin-payment/payment-statistics-data', function (data) {
            const stats = filter === 'monthly' ? data.monthlyPayments : data.dailyPayments;
            updateTable(stats, filter);
        });
    }

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

    fetchData('monthly');
    $('#filter').on('change', function () {
        const filter = $(this).val();
        fetchData(filter);
    });
});
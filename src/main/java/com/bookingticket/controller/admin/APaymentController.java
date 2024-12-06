package com.bookingticket.controller.admin;

import com.bookingticket.entity.Payment;
import com.bookingticket.repository.PaymentRepository;
import com.bookingticket.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


@Controller
@RequestMapping("/admin-payment")
public class APaymentController {

    private final PaymentService ApaymentService;

    public APaymentController(PaymentService ApaymentService) {
        this.ApaymentService = ApaymentService;
    }


    @GetMapping("/payment-statistics")
    public String getPaymentStatistics(Model model) {
        // Fetch the payment statistics data
        Map<String, List<Integer>> paymentData = getPaymentStatisticsData();

        // Add the data to the Model
        model.addAttribute("monthlyPayments", paymentData.get("monthlyPayments"));
        model.addAttribute("dailyPayments", paymentData.get("dailyPayments"));

        return "admin/statistical";
    }

    @GetMapping("/payment-statistics-data")
    @ResponseBody
    public Map<String, List<Integer>> getPaymentStatisticsData() {
        List<Payment> payments = ApaymentService.getAllPayments2();

        // Process payments to get monthly and daily data
        List<Integer> monthlyPayments = getMonthlyPayments(payments);
        List<Integer> dailyPayments = getDailyPayments(payments);

        // Return data as a JSON map
        Map<String, List<Integer>> responseData = new HashMap<>();
        responseData.put("monthlyPayments", monthlyPayments);
        responseData.put("dailyPayments", dailyPayments);

        return responseData;  // Return the data as JSON
    }

    private List<Integer> getMonthlyPayments(List<Payment> payments) {
        // Logic to sum payments by month
        List<Integer> result = new ArrayList<>(Collections.nCopies(12, 0)); // Initialize with 0 for each month
        for (Payment payment : payments) {
            int month = payment.getPayment_time().getMonthValue() - 1; // Adjust for zero indexing
            result.set(month, result.get(month) + payment.getAmount().intValue());
        }
        return result;
    }

    private List<Integer> getDailyPayments(List<Payment> payments) {
        // Logic to sum payments by day (assuming payments within a month)
        List<Integer> result = new ArrayList<>(Collections.nCopies(31, 0)); // Initialize with 0 for each day
        for (Payment payment : payments) {
            int day = payment.getPayment_time().getDayOfMonth() - 1; // Adjust for zero indexing
            result.set(day, result.get(day) + payment.getAmount().intValue());
        }
        return result;
    }

//    @GetMapping("/payment-chart")
//    public void generatePaymentChart(@RequestParam("viewType") String viewType, HttpServletResponse response) throws IOException {
//        // Lấy danh sách tất cả các payment
//        List<Payment> payments = ApaymentService.getAllPayments2();
//
//        // Tạo dataset dựa trên viewType
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        if ("monthly".equalsIgnoreCase(viewType)) {
//            Map<String, Integer> monthlyData = getMonthlyPaymentData(payments);
//            for (Map.Entry<String, Integer> entry : monthlyData.entrySet()) {
//                dataset.addValue(entry.getValue(), "Thanh Toán (VND)", entry.getKey());
//            }
//        } else if ("daily".equalsIgnoreCase(viewType)) {
//            Map<String, Integer> dailyData = getDailyPaymentData(payments);
//            for (Map.Entry<String, Integer> entry : dailyData.entrySet()) {
//                dataset.addValue(entry.getValue(), "Thanh Toán (VND)", entry.getKey());
//            }
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loại thống kê không hợp lệ");
//        }
//
//        // Tạo biểu đồ
//        String chartTitle = "Thống Kê Thanh Toán Theo " + ("monthly".equalsIgnoreCase(viewType) ? "Tháng" : "Ngày");
//        String xAxisLabel = "monthly".equalsIgnoreCase(viewType) ? "Tháng" : "Ngày";
//        JFreeChart chart = ChartFactory.createBarChart(
//                chartTitle,   // Tiêu đề
//                xAxisLabel,   // Nhãn trục X
//                "Số Tiền (VND)", // Nhãn trục Y
//                dataset,      // Dataset
//                PlotOrientation.VERTICAL, // Hướng biểu đồ
//                false, true, false // Các tùy chọn legend, tooltips, URLs
//        );
//
//        // Cấu hình màu sắc
//        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(54, 162, 235));
//
//        // Xuất biểu đồ dưới dạng PNG
//        response.setContentType("image/png");
//        ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
//    }
//
//    // Hàm xử lý dữ liệu theo tháng
//    private Map<String, Integer> getMonthlyPaymentData(List<Payment> payments) {
//        Map<String, Integer> monthlyData = new TreeMap<>();
//        payments.forEach(payment -> {
//            String month = "Tháng " + payment.getPayment_time().getMonthValue();
//            monthlyData.put(month, monthlyData.getOrDefault(month, 0) + payment.getAmount().intValue());
//        });
//        return monthlyData;
//    }
//
//    // Hàm xử lý dữ liệu theo ngày
//    private Map<String, Integer> getDailyPaymentData(List<Payment> payments) {
//        Map<String, Integer> dailyData = new TreeMap<>();
//        payments.forEach(payment -> {
//            String day = "Ngày " + payment.getPayment_time().getDayOfMonth();
//            dailyData.put(day, dailyData.getOrDefault(day, 0) + payment.getAmount().intValue());
//        });
//        return dailyData;
//    }
}


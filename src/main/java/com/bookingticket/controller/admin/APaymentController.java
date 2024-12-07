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
import java.util.stream.Collectors;


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
        Map<String, Map<String, Integer>> paymentData = getPaymentStatisticsData();

        // Add the data to the Model
        model.addAttribute("dailyPayments", paymentData.get("dailyPayments"));
        model.addAttribute("monthlyPayments", paymentData.get("monthlyPayments"));
        model.addAttribute("yearlyPayments", paymentData.get("yearlyPayments"));

        return "admin/statistical";
    }


    @GetMapping("/payment-statistics-data")
    @ResponseBody
    public Map<String, Map<String, Integer>> getPaymentStatisticsData() {
        // Lấy danh sách tất cả thanh toán từ dịch vụ
        List<Payment> payments = ApaymentService.getAllPayments2();

        // Lọc các thanh toán có trạng thái "success"
        List<Payment> successfulPayments = payments.stream()
                .filter(payment -> "success".equalsIgnoreCase(payment.getStatus()))
                .collect(Collectors.toList());

        // Tính toán doanh thu
        Map<String, Integer> dailyPayments = getPaymentsByDay(successfulPayments);
        Map<String, Integer> monthlyPayments = getPaymentsByMonth(successfulPayments);
        Map<String, Integer> yearlyPayments = getPaymentsByYear(successfulPayments);

        // Trả dữ liệu dưới dạng JSON
        Map<String, Map<String, Integer>> responseData = new HashMap<>();
        responseData.put("dailyPayments", dailyPayments);
        responseData.put("monthlyPayments", monthlyPayments);
        responseData.put("yearlyPayments", yearlyPayments);

        return responseData;
    }

    //Thống kê theo ngày
    private Map<String, Integer> getPaymentsByDay(List<Payment> payments) {
        Map<String, Integer> dailyPayments = new HashMap<>();
        for (Payment payment : payments) {
            String day = payment.getPayment_time().toLocalDate().toString(); // yyyy-MM-dd
            dailyPayments.put(day, dailyPayments.getOrDefault(day, 0) + payment.getAmount().intValue());
        }
        return dailyPayments;
    }

    //Thống kê theo tháng
    private Map<String, Integer> getPaymentsByMonth(List<Payment> payments) {
        Map<String, Integer> monthlyPayments = new HashMap<>();
        for (Payment payment : payments) {
            String month = payment.getPayment_time().getYear() + "-" + String.format("%02d", payment.getPayment_time().getMonthValue()); // yyyy-MM
            monthlyPayments.put(month, monthlyPayments.getOrDefault(month, 0) + payment.getAmount().intValue());
        }
        return monthlyPayments;
    }

    //Thống kê theo năm
    private Map<String, Integer> getPaymentsByYear(List<Payment> payments) {
        Map<String, Integer> yearlyPayments = new HashMap<>();
        for (Payment payment : payments) {
            String year = String.valueOf(payment.getPayment_time().getYear()); // yyyy
            yearlyPayments.put(year, yearlyPayments.getOrDefault(year, 0) + payment.getAmount().intValue());
        }
        return yearlyPayments;
    }

}


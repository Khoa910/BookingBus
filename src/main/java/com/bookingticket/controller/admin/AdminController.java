package com.bookingticket.controller.admin;

import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.BusScheduleRespond;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.BusSchedule;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.service.BusScheduleService;
import com.bookingticket.service.BusService;
import com.bookingticket.service.RoleService;
import com.bookingticket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping( "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final BusScheduleService AbusScheduleService;
    private final BusService AbusService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(UserService userService, RoleService roleService, BusScheduleService AbusScheduleService, BusService AbusService) {
        this.userService = userService;
        this.roleService = roleService;
        this.AbusScheduleService = AbusScheduleService;
        this.AbusService = AbusService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public String admin() {
        return "admin/index";
    }

//    @GetMapping("/user")
//    public String showUsers(Model model) {
//        List<UserRespond> users = userService.getAllUsers();
//        model.addAttribute("users", users); // Đẩy danh sách user vào model
//        List<RoleRespond> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles); // Đẩy danh sách vai trò vào model
//        return "/admin/user-list"; // Trả về tên file HTML trong thư mục templates
//    }

    @GetMapping("/user")
    public String getAllUser(Model model) {
        List<User> users = userService.getAllUsers();
        logger.info("Total customers: {}", users.size());
        model.addAttribute("users", users);
        List<Role> roles = roleService.getAllRoles();
        logger.info("Total customers: {}", roles.size());
        model.addAttribute("roles", roles);
        return "admin/user-list"; // Trang hiển thị danh sách khách hàng
    }

    @GetMapping("/user/listUser")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUserJson() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No accounts found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users); // Trả về 204 nếu không có tài khoản
        } else {
            logger.info("Total accounts: {}", users.size());
            users.forEach(user -> logger.info("User: {}", user)); // Ghi từng tài khoản
            return ResponseEntity.ok(users); // Trả về danh sách tài khoản với mã 200
        }
    }

    @PostMapping("/user/add")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody Map<String, Object> accountData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ accountData
            String username = (String) accountData.get("username");
            String password = (String) accountData.get("password");
            String fullName = (String) accountData.get("full_name");
            String phoneNumber = (String) accountData.get("phone_number");
            String email = (String) accountData.get("email");
            String address = (String) accountData.get("address");
            String roleName = (String) accountData.get("role");

            // Lấy đối tượng Role từ service dựa trên roleName
            Role role = roleService.getRoleByName(roleName);

            // Tạo đối tượng User mới
            User newAccount = new User();
            newAccount.setUsername(username);
            newAccount.setPassword(password); // Cần mã hóa mật khẩu trước khi lưu
            newAccount.setFull_name(fullName);
            newAccount.setPhone_number(phoneNumber);
            newAccount.setEmail(email);
            newAccount.setAddress(address);
            newAccount.setRole(role);

            // Gọi service để thêm tài khoản
            boolean added = userService.addAccount(newAccount);
            if (added) {
                logger.info("Account with name {} added successfully.", username);
                response.put("message", "Tài khoản đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Thêm tài khoản thất bại!"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

    // Phương thức kiểm tra tính hợp lệ của email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<User> getAccountById(@PathVariable long id) {
        Optional<User> account = userService.getAccountById(id);
        if (account == null) {
            logger.warn("Account with ID {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("Account found: {}", account.get());
        return ResponseEntity.ok(account.get());
    }

    @PutMapping("/user/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        boolean updated = userService.updateUser(user);
        if (updated) {
            return ResponseEntity.ok("Cập nhật khách hàng thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng.");
        }
    }

}
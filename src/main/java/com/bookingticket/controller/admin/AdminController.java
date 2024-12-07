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
import jakarta.servlet.http.HttpSession;
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

import java.util.*;

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

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("")
//    public String admin(HttpSession session) {
//        String role = (String) session.getAttribute("role");
//
//        // Kiểm tra nếu role là ADMIN, nếu không chuyển hướng về trang khác
//        if ("ADMIN".equals(role)) {
//            return "admin/index"; // Trả về trang admin nếu là ADMIN
//        } else {
//            return "redirect:/error403"; // Chuyển hướng đến trang lỗi nếu không phải ADMIN
//        }
//    }

    @GetMapping()
    public String admin() {return "admin/index";}

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
        List<User> users = userService.getAllUsers2();
        logger.info("Total customers: {}", users.size());
        model.addAttribute("users", users);
        List<Role> roles = roleService.getAllRoles();
        logger.info("Total customers: {}", roles.size());
        model.addAttribute("roles", roles);
        return "admin/user-list"; // Trang hiển thị danh sách khách hàng
    }

    @GetMapping("/user/listUser")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllAccountJson() {
        List<User> accounts = userService.getAllUsers2(); // Lấy danh sách người dùng từ service
        List<Map<String, String>> accountList = new ArrayList<>();

        if (accounts.isEmpty()) {
            logger.warn("No accounts found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountList); // Trả về 204 nếu không có tài khoản
        } else {
            logger.info("Total accounts: {}", accounts.size());
            accounts.forEach(account -> {
                // Tạo Map đại diện cho từng người dùng
                Map<String, String> userMap = new HashMap<>();
                userMap.put("idU", String.valueOf(account.getId()));
                userMap.put("username", account.getUsername());
                userMap.put("password", account.getPassword());
                userMap.put("full_nameA", account.getFull_name());
                userMap.put("phone_numberA", account.getPhone_number());
                userMap.put("email", account.getEmail());
                userMap.put("address", account.getAddress());

                // Lấy thông tin role nếu tồn tại
                if (account.getRole() != null) {
                    userMap.put("role_name", account.getRole().getName());
                } else {
                    userMap.put("role_name", "N/A");
                }

                // Thêm vào danh sách
                accountList.add(userMap);

                // Ghi log thông tin
                logger.info("Account ID: {}, Username: {}, Role: {}",
                        account.getId(),
                        account.getUsername(),
                        account.getRole() != null ? account.getRole().getName() : "N/A");
            });

            // Trả về danh sách tài khoản với mã 200
            return ResponseEntity.ok(accountList);
        }
    }


    public ResponseEntity<List<User>> getAllCustomersJson() {
        List<User> customers = userService.getAllUsers2();
        if (customers.isEmpty()) {
            logger.warn("No customers found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/user/add")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody Map<String, Object> accountData) {
        Map<String, String> response = new HashMap<>();
        try {
            // Lấy các thông tin từ accountData
            String username = (String) accountData.get("username");
            String password = (String) accountData.get("password");
            String fullName = (String) accountData.get("full_name");
            String phoneNumber = (String) accountData.get("phone_Number");
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

    @PutMapping("/user/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateAccount(@PathVariable Integer id, @RequestBody Map<String, Object> accountData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String accountId = (String) accountData.get("id");
            Long accountIdLong = Long.parseLong(accountId);
            String username = (String) accountData.get("username");
            String fullName = (String) accountData.get("full_name");
            String phoneNumber = (String) accountData.get("phone_Number");
            String email = (String) accountData.get("email");
            String address = (String) accountData.get("address");
            String roleName = (String) accountData.get("role");

            Role role = roleService.getRoleByName(roleName);

            // Kiểm tra xem tất cả các trường cần thiết đều có giá trị
//            if (accountName == null || accountName.isEmpty() ||
//                    email == null || email.isEmpty() ||
//                    password == null || password.isEmpty() || // Kiểm tra mật khẩu
//                    status == null) {
//                response.put("message", "Vui lòng điền đầy đủ thông tin!");
//                return ResponseEntity.badRequest().body(response);
//            }
//
//            // Kiểm tra tính hợp lệ của địa chỉ email
//            if (!isValidEmail(email)) {
//                response.put("message", "Địa chỉ email không hợp lệ!");
//                return ResponseEntity.badRequest().body(response);
//            }

            // Lấy Account từ ID
            Optional<User> optionalAccount = userService.getAccountById(accountIdLong);
            if (!optionalAccount.isPresent()) {
                response.put("message", "Tài khoản không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            User existingAccount = optionalAccount.get();
            existingAccount.setUsername(username);
            existingAccount.setFull_name(fullName);
            existingAccount.setPhone_number(phoneNumber);
            existingAccount.setEmail(email);
            existingAccount.setAddress(address);
            existingAccount.setRole(role);

            // Gọi service để cập nhật tài khoản
            boolean updated = userService.updateUser2(existingAccount);
            if (updated) {
                logger.info("Account with ID {} updated successfully.", existingAccount.getId());
                response.put("message", "Tài khoản đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update account with ID {}.", existingAccount.getId());
                response.put("message", "Cập nhật tài khoản thất bại!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found with id: " + id);
        }
    }

}
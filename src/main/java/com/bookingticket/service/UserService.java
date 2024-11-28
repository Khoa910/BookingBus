package com.bookingticket.service;

import com.bookingticket.dto.request.LoginRequest;
import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.UserMapper;
import com.bookingticket.repository.RoleRepository;
import com.bookingticket.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserRespond> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toRespond)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers2() {
        return userRepository.findAll();
    }

    public UserRespond getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::toRespond)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserRespond createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.toRespond(savedUser);
    }

    public boolean addAccount(User account) {
        try {
            userRepository.save(account); // Lưu tài khoản mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public Optional<User> getAccountById(long accountId) {
        return userRepository.findById(accountId);
    }

//    public UserRespond updateUser(Long id, UserRequest userRequest) {
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setUsername(userRequest.getUsername());
//            user.setFull_name(userRequest.getFull_name());
//            user.setPhone_number(userRequest.getPhone_number());
//            user.setEmail(userRequest.getEmail());
//            user.setAddress(userRequest.getAddress());
//
//            if (userRequest.getRole() != null) {
//                Optional<Role> roleOptional = roleRepository.findById(userRequest.getRole());
//                if (roleOptional.isPresent()) {
//                    user.setRole(roleOptional.get());
//                } else {
//                    throw new RuntimeException("Role not found with id: " + userRequest.getRole());
//                }
//            } else {
//                user.setRole(null);
//            }
//
//            User updatedUser = userRepository.save(user);
//            return userMapper.toRespond(updatedUser);
//        } else {
//            throw new RuntimeException("User not found with id: " + id);
//        }
//    }

    public boolean updateUser(User userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setFull_name(userDetails.getFull_name());
            user.setPhone_number(userDetails.getPhone_number());
            user.setEmail(userDetails.getEmail());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Xóa một người dùng
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    public UserRespond registerUser(UserRequest userRequest) {
        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại: " + userRequest.getUsername());
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email đăng nhập đã tồn tại: " + userRequest.getUsername());
        }


        // Lấy role mặc định (có thể điều chỉnh id nếu cần)
        Role role = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        // Chuyển đổi UserRequest thành User entity
        User user = userMapper.toEntity(userRequest);

        // Mã hóa mật khẩu trước khi lưu (cần tích hợp công cụ mã hóa, ví dụ BCrypt)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(role);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);
        try {
            emailService.sendWelcomeEmail(
                    savedUser.getEmail(),          // Địa chỉ email
                    savedUser.getFull_name(),       // Tên người dùng
                    savedUser.getUsername()        // Tên đăng nhập
            );
        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email, nếu cần
            System.out.println(e.getMessage());
            throw new RuntimeException("Lỗi khi gửi email chào mừng: " + e.getMessage());
        }

        // Trả về phản hồi dưới dạng DTO
        return userMapper.toRespond(savedUser);
    }
    public UserRespond loginUser(LoginRequest loginRequest) {
        // Kiểm tra người dùng tồn tại theo email
        User user = (User) userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại: " + loginRequest.getEmail()));

        // Kiểm tra mật khẩu (giả lập hoặc sử dụng mã hóa BCrypt để so sánh)
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email hoặc mật khẩu không chính xác");
        }

        // Trả về phản hồi người dùng
        return userMapper.toRespond(user);
    }
    public void forgotPassword(String email) {
        // Kiểm tra email có tồn tại trong hệ thống
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại trong hệ thống: " + email));

        // Sinh mật khẩu mới ngẫu nhiên
        String newPassword = generateRandomPassword();

        // Mã hóa mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));

        // Cập nhật lại mật khẩu vào cơ sở dữ liệu
        userRepository.save(user);

        // Gửi email thông báo mật khẩu mới
        try {
            emailService.sendNewPasswordEmail(
                    user.getEmail(),
                    user.getFull_name(),
                    newPassword
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email chứa mật khẩu mới: " + e.getMessage());
        }
    }

    private String generateRandomPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int PASSWORD_LENGTH = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }



}
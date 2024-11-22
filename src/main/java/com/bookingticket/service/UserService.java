package com.bookingticket.service;

import com.bookingticket.dto.request.RequestRegister;
import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.respond.UserRespond;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.UserMapper;
import com.bookingticket.repository.RoleRepository;
import com.bookingticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean registerUser(RequestRegister request) {
        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            return false; // Tên đăng nhập đã tồn tại
        }
        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        // Tạo đối tượng User từ RequestRegister
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Lưu ý: Mã hóa mật khẩu trước khi lưu
        user.setFull_name(request.getFullName());
        user.setPhone_number(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setRole(role);
        // Lưu vào cơ sở dữ liệu
        userRepository.save(user);
        return true;
    }
    public List<UserRespond> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toRespond)
                .collect(Collectors.toList());
    }
}

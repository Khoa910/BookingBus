package com.bookingticket.service;

import com.bookingticket.dto.request.RequestRegister;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.repository.RoleRepository;
import com.bookingticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(RequestRegister dto) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role không tồn tại"));
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Mã hóa mật khẩu
        user.setFull_name(dto.getFullName());
        user.setPhone_number(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setRole(role);
        // Lưu người dùng
        userRepository.save(user);
    }
}

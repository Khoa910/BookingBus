//package com.bookingticket.Repository;
//
//import com.bookingticket.entity.Role;
//import com.bookingticket.entity.User;
//import com.bookingticket.repository.RoleRepository;
//import com.bookingticket.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class UserTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//
//
//    @Test
//    public void testAddUser() {
//        // Tìm kiếm role theo id, ví dụ "Admin" với ID là 1L
//        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role không tồn tại"));
//
//        // Tạo mới User và gán các thuộc tính
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("password123");  // Đảm bảo mật khẩu được mã hóa
//        user.setFull_name("Test User");
//        user.setPhone_number("123456789");
//        user.setEmail("testuser@example.com");
//        user.setAddress("123 Test Street");
//
//        // Gán Role cho User dựa trên ID Role
//        user.setRole(role);  // Gán Role cho User, Role đã tìm thấy từ ID
//
//        // Lưu User vào cơ sở dữ liệu
//        userRepository.save(user);
//
//        // Kiểm tra xem User đã được thêm vào cơ sở dữ liệu chưa
//        User savedUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User không tồn tại"));
//
//        assertNotNull(savedUser, "User không được tìm thấy");
//        assertEquals("testUser", savedUser.getUsername(), "Tên người dùng không đúng");
//        assertEquals("Test User", savedUser.getFull_name(), "Tên đầy đủ không đúng");
//        assertEquals(role.getId(), savedUser.getRole().getId(), "Role của User không đúng");
//    }
//
//
//    @Test
//    public void testUpdateUser() {
//        // Giả sử người dùng với ID = 1 đã tồn tại trong cơ sở dữ liệu
//        User userToUpdate = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User không tồn tại"));
//
//        // Cập nhật thông tin của User
//        userToUpdate.setUsername("john_updated");
//        userToUpdate.setFull_name("John Updated");
//        userToUpdate.setPhone_number("987654321");
//        userToUpdate.setEmail("john.updated@example.com");
//        userToUpdate.setAddress("456 New St");
//
//        // Lưu User đã cập nhật
//        User updatedUser = userRepository.save(userToUpdate);
//
//        // Kiểm tra xem User đã được cập nhật thành công
//        assertNotNull(updatedUser.getId(), "The updated User should have an ID");
//        assertEquals("john_updated", updatedUser.getUsername(), "The username should be updated correctly");
//        assertEquals("John Updated", updatedUser.getFull_name(), "The full name should be updated correctly");
//        assertEquals("987654321", updatedUser.getPhone_number(), "The phone number should be updated correctly");
//        assertEquals("john.updated@example.com", updatedUser.getEmail(), "The email should be updated correctly");
//        assertEquals("456 New St", updatedUser.getAddress(), "The address should be updated correctly");
//
//        // In ra thông tin của User đã cập nhật
//        System.out.println("Updated User ID: " + updatedUser.getId());
//        System.out.println("Updated User Username: " + updatedUser.getUsername());
//        System.out.println("Updated User Full Name: " + updatedUser.getFull_name());
//    }
//
//    @Test
//    public void testDeleteUser() {
//        // Giả sử bạn có một ID đã tồn tại trong cơ sở dữ liệu (ID là 1)
//        Long existingId = 1L;
//
//        // Kiểm tra xem User với ID đó có tồn tại không
//        assertTrue(userRepository.findById(existingId).isPresent(), "User with ID " + existingId + " should exist");
//
//        // Xóa User bằng ID
//        userRepository.deleteById(existingId);
//
//        // Kiểm tra lại xem User đã bị xóa chưa
//        assertFalse(userRepository.findById(existingId).isPresent(), "User with ID " + existingId + " should be deleted");
//
//        System.out.println("User with ID " + existingId + " has been deleted.");
//    }
//}

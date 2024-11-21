//package com.bookingticket.mapper;
//
//import com.bookingticket.dto.request.UserRequest;
//import com.bookingticket.dto.respond.UserRespond;
//import com.bookingticket.entity.User;
//import com.bookingticket.entity.Role;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Import(TestConfig.class)
//public class UserMapperTest {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testToEntity() {
//        // Khởi tạo Role mẫu
////        Role role = new Role(1L, "ADMIN");
//
//        // Khởi tạo UserRequest mẫu
//        UserRequest userRequest = new UserRequest(
//                "username123",  // username
//                "password123",  // password
//                "John Doe",  // full_name
//                "1234567890",  // phone_number
//                "john@example.com",  // email
//                "123 Street Name",  // address
//                1L  // role_id (Ánh xạ vào Role entity)
//        );
//
//        // Chuyển đổi UserRequest thành User entity
//        User userEntity = userMapper.toEntity(userRequest);
//
//        // Kiểm tra các trường trong User entity đã được ánh xạ đúng
//        assertNotNull(userEntity);  // Đảm bảo đối tượng không null
//        assertEquals("username123", userEntity.getUsername());  // Kiểm tra username
//        assertEquals("John Doe", userEntity.getFull_name());  // Kiểm tra full_name
//        assertEquals("john@example.com", userEntity.getEmail());  // Kiểm tra email
//        assertEquals("123 Street Name", userEntity.getAddress());  // Kiểm tra address
//        assertEquals(1L, userEntity.getRole().getId());  // Kiểm tra role_id ánh xạ đúng vào Role entity
//    }
//
//    @Test
//    public void testToRespond() {
//        // Khởi tạo Role mẫu
//        Role role = new Role(1L, "ADMIN");
//
//        // Khởi tạo User entity mẫu
//        User user = new User(
//                1L,  // id
//                "username123",  // username
//                "password123",  // password (Sẽ được ẩn trong DTO)
//                "John Doe",  // full_name
//                "1234567890",  // phone_number
//                "john@example.com",  // email
//                "123 Street Name",  // address
//                role,  // role
//                null  // tickets
//        );
//
//        // Chuyển đổi User entity thành UserRespond DTO
//        UserRespond userRespond = userMapper.toRespond(user);
//
//        // Kiểm tra các trường trong UserRespond đã được ánh xạ đúng
//        assertNotNull(userRespond);  // Đảm bảo đối tượng không null
//        assertEquals(1L, userRespond.getId());  // Kiểm tra id
//        assertEquals("username123", userRespond.getUsername());  // Kiểm tra username
//        assertEquals("John Doe", userRespond.getFull_name());  // Kiểm tra full_name
//        assertEquals("john@example.com", userRespond.getEmail());  // Kiểm tra email
//        assertEquals("123 Street Name", userRespond.getAddress());  // Kiểm tra address
//        assertEquals(1L, userRespond.getRole().getId());  // Kiểm tra role_id trong DTO
//    }
//}

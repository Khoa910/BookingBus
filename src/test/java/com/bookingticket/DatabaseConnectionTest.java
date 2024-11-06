package com.bookingticket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        // Kiểm tra xem kết nối cơ sở dữ liệu có tồn tại không
        assertNotNull(jdbcTemplate, "JdbcTemplate should not be null");

        // Thực hiện một truy vấn kiểm tra đơn giản
        String sql = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);

        // Kiểm tra kết quả của truy vấn
        assertNotNull(result, "Query result should not be null");
        assert(result == 1);
    }
}
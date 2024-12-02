package com.bookingticket.repository;

import com.bookingticket.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<Object> findByEmail(@NotBlank(message = "Email không được để trống") @Email(message = "Email không hợp lệ") String email);

    @Override
    boolean existsById(Long aLong);

    boolean existsByEmail(@Email(message = "Email không hợp lệ") String email);

    User findUserByUsername(String username);
}

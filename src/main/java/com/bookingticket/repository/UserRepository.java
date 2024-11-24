package com.bookingticket.repository;

import com.bookingticket.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<Object> findByEmail(@NotBlank(message = "Email không được để trống") @Email(message = "Email không hợp lệ") String email);

    boolean existsByEmail(@Email(message = "Email không hợp lệ") String email);
}

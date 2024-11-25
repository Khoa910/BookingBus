package com.bookingticket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestOAuth {
    Long ID;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    String password;

    @NotBlank(message = "Họ và tên không được để trống")
    String full_name;

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10 hoặc 11 chữ số")
    String phone_number;

    @Email(message = "Email không hợp lệ")
    String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    String address;

    Long role;
}

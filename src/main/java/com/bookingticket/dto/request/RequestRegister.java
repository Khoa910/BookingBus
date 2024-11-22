package com.bookingticket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestRegister {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
}

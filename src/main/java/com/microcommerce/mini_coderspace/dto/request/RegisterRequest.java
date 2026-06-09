package com.microcommerce.mini_coderspace.dto.request;

import com.microcommerce.mini_coderspace.enums.UserType;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private UserType userType;
}

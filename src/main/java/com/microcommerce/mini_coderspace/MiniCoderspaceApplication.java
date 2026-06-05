package com.microcommerce.mini_coderspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class MiniCoderspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCoderspaceApplication.class, args);
    }

}

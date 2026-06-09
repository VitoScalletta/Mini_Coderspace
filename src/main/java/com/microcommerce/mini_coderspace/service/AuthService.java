package com.microcommerce.mini_coderspace.service;

import com.microcommerce.mini_coderspace.dto.request.LoginRequest;
import com.microcommerce.mini_coderspace.dto.request.RegisterRequest;
import com.microcommerce.mini_coderspace.dto.response.AuthResponse;
import com.microcommerce.mini_coderspace.entity.User;
import com.microcommerce.mini_coderspace.repository.UserRepository;
import com.microcommerce.mini_coderspace.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final JwtService jwtService;
     private final AuthenticationManager authenticationManager;

     public AuthResponse register(RegisterRequest request) {
          User user = new User();
          user.setName(request.getName());
          user.setEmail(request.getEmail());
          user.setUserType(request.getUserType());
          user.setPassword(passwordEncoder.encode(request.getPassword()));

          userRepository.save(user);
          String token = jwtService.generateToken(user);
          return new AuthResponse(token);
     }

     public AuthResponse login(LoginRequest request) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
         );
         User user = userRepository.findByEmail(request.getEmail())
                 .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

         String token = jwtService.generateToken(user);
         return new AuthResponse(token);
     }
}

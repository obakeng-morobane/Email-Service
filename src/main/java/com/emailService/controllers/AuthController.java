package com.emailService.controllers;

import com.emailService.DTO.LoginResponseDTO;
import com.emailService.DTO.RegistrationDTO;
import com.emailService.services.AuthService;
import com.emailService.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody RegistrationDTO DTO){
        return authService.loginUser(DTO.getEmail(), DTO.getPassword());
    }
}

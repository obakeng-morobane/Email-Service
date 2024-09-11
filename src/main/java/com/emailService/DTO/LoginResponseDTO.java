package com.emailService.DTO;

import com.emailService.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    private User user;
    private String jwt;
}

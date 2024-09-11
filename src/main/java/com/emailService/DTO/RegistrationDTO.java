package com.emailService.DTO;

import com.emailService.models.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
}

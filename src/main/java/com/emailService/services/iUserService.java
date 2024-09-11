package com.emailService.services;

import com.emailService.DTO.RegistrationDTO;
import com.emailService.exceptions.UserExistException;
import com.emailService.models.User;

import java.util.List;
import java.util.Optional;

public interface iUserService {

    List<User> getUsers();
    User registerUser(RegistrationDTO registerDTO);
    User findByEmail(String email);



}

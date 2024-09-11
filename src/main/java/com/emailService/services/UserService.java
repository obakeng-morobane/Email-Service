package com.emailService.services;

import com.emailService.DTO.RegistrationDTO;
import com.emailService.models.Role;
import com.emailService.models.User;
import com.emailService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements iUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public User registerUser(RegistrationDTO registerDTO)  {
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        var user = new User(registerDTO.getFirstName()
                ,registerDTO.getLastName()
                ,registerDTO.getEmail()
                ,encodedPassword
                ,Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }




}

package com.emailService.services;

import com.emailService.DTO.LoginResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final iUserService iuserService;

    public LoginResponseDTO loginUser(String username, String password){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = jwtService.generateJwt(auth);
            // will execute loginResponseDTO which will display all the information about the user
            //demonstration purpose
            return  new LoginResponseDTO(iuserService.findByEmail(username),token);
        }catch (AuthenticationException e){
            return new LoginResponseDTO(null, "Jwt not generated because of wrong login info");
        }
    }

}

package com.emailService.controllers;

import com.emailService.DTO.RegistrationDTO;
import com.emailService.event.RegistrationComplete;
import com.emailService.event.ResetComplete;
import com.emailService.models.User;
import com.emailService.models.VerificationToken;

import com.emailService.services.PasswordResetServices;
import com.emailService.services.UserService;
import com.emailService.services.VerificationTokenService;
import com.emailService.utils.ApplicationUrl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final PasswordResetServices passwordResetServices;

    // method to register user
    @PostMapping("/user")
    public String registerUser(@RequestBody RegistrationDTO registerDTO, final HttpServletRequest request) {
        User user = userService.registerUser(registerDTO);
        // publish registration event
        eventPublisher.publishEvent(new RegistrationComplete(user, ApplicationUrl.getApplicationUrl(request)));
        return "Success! Please check your email to complete your registration";
    }

    // method to verify email with the token sent to enable user
    @GetMapping("/verifyToken")
    public String verifyEmail(@RequestParam("token") String token){
    VerificationToken theToken = tokenService.findByToken(token);
    if (theToken.getUser().isEnabled()){
            return "This account is already verified, please login";
        }
        String verificationResult = tokenService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can log in to your account";
        }
        return "SOMETHING WRONG with the token";
    }


    //method to check if email exist before sending verification email
    // if email exist then email will be sent with verification token
    @PostMapping("/mailCheck")
    public String resetPasswordRequest(@RequestBody RegistrationDTO DTO, final HttpServletRequest request){
        String email = DTO.getEmail();
        User user = userService.findByEmail(email);
        if (user == null){
            return "Email does not exist {} " + email;
        }
        eventPublisher.publishEvent(new ResetComplete(user, ApplicationUrl.getApplicationUrl(request)));
        return "Email sent, please click on link to confirm email to reset password";
    }

    // verify token sent to verify email of user
    @GetMapping("/verifyPasswordToken")
    public String verifyPassword(@RequestParam("token") String token, final HttpServletRequest request){
        log.info("The token verified is Token : {}", token);
        String verificationResult = passwordResetServices.validatePasswordResetToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
           log.info("Email verified successfully. Now you can Reset your password: {} ", verificationResult);
        }
        return ApplicationUrl.getApplicationUrl(request)+"/register/reset-password?token="+token;

    }

    // Method to reset password
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token,@RequestBody RegistrationDTO DTO  ,final HttpServletRequest request) {
        String password = DTO.getPassword();
        User theUser = passwordResetServices.findUserByPasswordResetToken(token);
        return new ResponseEntity<>(passwordResetServices.resetPassword(theUser, password), HttpStatus.OK);
    }

}

package com.emailService.services;

import com.emailService.models.PasswordReset;
import com.emailService.models.User;
import com.emailService.repository.PasswordResetRepository;
import com.emailService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class PasswordResetServices implements iPasswordResetServices {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int EXPIRATION_TIME = 15;
    public static Date getExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
    @Override
    public void createPasswordResetTokenForUser(User user, String passwordReset) {
        PasswordReset resetToken = new PasswordReset(passwordReset, user);
        passwordResetRepository.save(resetToken);
    }





    @Override
    public String validatePasswordResetToken(String theToken) {

        PasswordReset passwordResetToken = passwordResetRepository.findByToken(theToken);
        if (passwordResetToken == null){
            return "invalid string";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            return "expired";
        }

        return "/reset-password";
    }


    @Override
    public User findUserByPasswordResetToken(String theToken) {
        return passwordResetRepository.findByToken(theToken).getUser();
    }

    @Override
    public String resetPassword(User theUser, String password) {
        if (theUser != null){
            theUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(theUser);
            System.out.println("Password has been reset");
        }
        return "Successful";
    }
}

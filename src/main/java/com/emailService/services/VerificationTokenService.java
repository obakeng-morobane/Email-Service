package com.emailService.services;

import com.emailService.models.User;
import com.emailService.models.VerificationToken;
import com.emailService.repository.UserRepository;
import com.emailService.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements iVerificationTokenService{

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private static final int EXPIRATION_TIME = 15;

    public static Date getExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if (token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calender = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calender.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token,user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }


}

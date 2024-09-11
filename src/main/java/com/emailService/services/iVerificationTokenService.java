package com.emailService.services;

import com.emailService.models.User;
import com.emailService.models.VerificationToken;

public interface iVerificationTokenService {
    String validateToken(String token);
    void saveVerificationToken(User user, String token);

    VerificationToken findByToken(String theToken);




}

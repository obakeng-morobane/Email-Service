package com.emailService.services;

import com.emailService.models.User;

public interface iPasswordResetServices {
    void createPasswordResetTokenForUser(User user, String passwordResetToken);

    String validatePasswordResetToken(String theToken);


    User findUserByPasswordResetToken(String theToken);

    String resetPassword(User theUser, String password);
}

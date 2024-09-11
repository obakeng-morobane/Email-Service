package com.emailService.event.listener;

import com.emailService.event.RegistrationComplete;
import com.emailService.event.ResetComplete;
import com.emailService.models.User;

import com.emailService.services.PasswordResetServices;
import com.emailService.services.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteListener implements ApplicationListener<RegistrationComplete> {


    private final VerificationTokenService tokenService;
    private final PasswordResetServices passwordResetServices;
    private final JavaMailSender mailSender;

    private User user;

    @Override
    public void onApplicationEvent(RegistrationComplete event) {
        user = event.getUser();
        String verificationTokeUUID = UUID.randomUUID().toString();
        tokenService.saveVerificationToken(user,verificationTokeUUID);
        String urlReg = event.getApplicationUrl() + "/register/verifyToken?token=" + verificationTokeUUID ;
        log.info("Click the link to verify your registration : {}", urlReg);
        try {
            sendVerificationEmail(urlReg);
        } catch (MessagingException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void ApplicationEvent(ResetComplete event) {
        user = event.getUser();
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetServices.createPasswordResetTokenForUser(user, passwordResetToken);

        String url = event.getApplicationUrl()+"/register/verifyPasswordToken?token="+passwordResetToken;
        // Sending password reset token to email
        log.info("Click the link to verify your registration : {}", url);
        try {
            sendPasswordResetVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration example";
        String mailContent =
                "<p> Hi, "+ user.getFirstName()+ ", </p>"
                        + "<p> Thank you for registering with us," + " "
                        + "Please, click the link below to complete your registration.</p>"
                        + "<a href=\"" +url+ "\" >Verify your email to activate your account </a>"
                        + "<p> Thank you <br> Users Registration example </p>";
        emailMessage(subject, senderName, mailContent,mailSender,user);
    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "User verification example";
        String mailContent =
                "<p> Hi, "+ user.getFirstName()+ ", </p>"
                        + "<p> You recently requested to reset your password," + " "
                        + "Please follow the link below to complete the action.</p>"
                        + "<a href=\"" +url+ "\" >Reset Password </a>"
                        + "<p> Thank you <br> User Password Reset example </p>";
        emailMessage(subject, senderName, mailContent,mailSender,user);
    }

    private static void emailMessage(String subject, String senderName, String mailContent
                                    , JavaMailSender mailSender, User user)
            throws MessagingException, UnsupportedEncodingException {


            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("obakengmorobane28@gmail.com", senderName);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        }


}



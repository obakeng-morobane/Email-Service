package com.emailService.event;

import com.emailService.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationComplete extends ApplicationEvent {
    private User user;
    private String applicationUrl;
    public RegistrationComplete(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;

    }
}

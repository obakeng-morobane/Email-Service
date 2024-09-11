package com.emailService.event;

import com.emailService.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResetComplete extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public ResetComplete(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }

}

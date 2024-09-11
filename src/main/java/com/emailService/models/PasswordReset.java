package com.emailService.models;

import com.emailService.services.PasswordResetServices;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordReset(String token, User user) {
        super();
        this.token = token;
        this.expirationTime = PasswordResetServices.getExpirationTime();
        this.user = user;
    }


}

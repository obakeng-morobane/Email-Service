package com.emailService.repository;

import com.emailService.models.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    PasswordReset findByToken(String theToken);
}

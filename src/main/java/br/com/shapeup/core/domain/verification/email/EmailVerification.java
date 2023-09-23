package br.com.shapeup.core.domain.verification.email;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.time.LocalDateTime;

public class EmailVerification extends Entity<EmailVerificationId> {

    String code;
    Boolean verified;
    String email;
    String username;
    LocalDateTime createdAt;
    LocalDateTime expiresIn;

    public EmailVerification(EmailVerificationId emailVerificationId,
                             String code,
                             Boolean verified,
                             String email,
                             String username,
                             LocalDateTime createdAt,
                             LocalDateTime expiresIn
    ) {
        super(emailVerificationId);
        this.code = code;
        this.verified = verified;
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}

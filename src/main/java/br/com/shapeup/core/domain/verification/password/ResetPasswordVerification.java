package br.com.shapeup.core.domain.verification.password;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.time.LocalDateTime;

public class ResetPasswordVerification extends Entity<ResetPasswordVerificationId> {

    private String code;
    private User user;
    private String status;
    private Boolean verified;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime expiresIn;

    protected ResetPasswordVerification(ResetPasswordVerificationId resetPasswordVerificationId) {
        super(resetPasswordVerificationId);
    }

    public ResetPasswordVerification(
            ResetPasswordVerificationId resetPasswordVerificationId,
            String code,
            User user,
            String status,
            Boolean verified,
            String email,
            LocalDateTime createdAt,
            LocalDateTime expiresIn
    ) {
        super(resetPasswordVerificationId);
        this.code = code;
        this.user = user;
        this.status = status;
        this.verified = verified;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

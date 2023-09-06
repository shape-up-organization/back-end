package br.com.shapeup.core.ports.input.verification;

public interface VerificationResetPasswordInput {
    void createVerificationCode(String email);
    void confirmResetPassword(String email, String code);
    Boolean isVerified(String email);
}

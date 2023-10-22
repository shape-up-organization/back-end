package br.com.shapeup.core.ports.input.verification;

public interface VerificationEmailInput {
        void confirmEmail(String email, String code);
        void createVerificationCode(String email);
        Boolean isVerified(String email);
}

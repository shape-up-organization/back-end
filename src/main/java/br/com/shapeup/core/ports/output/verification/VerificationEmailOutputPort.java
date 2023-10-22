package br.com.shapeup.core.ports.output.verification;

import br.com.shapeup.core.domain.verification.email.EmailVerification;

public interface VerificationEmailOutputPort {
    void makeAuthorized(String email);
    EmailVerification findByEmail(String username);
    EmailVerification save(EmailVerification emailVerification);
    Boolean isVerified(String email);
    void delete(EmailVerification verification);
}

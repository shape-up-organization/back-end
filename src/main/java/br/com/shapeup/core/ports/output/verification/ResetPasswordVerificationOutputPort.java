package br.com.shapeup.core.ports.output.verification;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerification;

public interface ResetPasswordVerificationOutputPort {
    void save(ResetPasswordVerification resetPasswordVerification);
    ResetPasswordVerification findByUserEmail(String email, User user);
    void makeAuthorized(String email);
}

package br.com.shapeup.adapters.output.integration.verification;

import br.com.shapeup.adapters.output.repository.jpa.verification.ResetPasswordVerificationRepository;
import br.com.shapeup.adapters.output.repository.mapper.verification.ResetPasswordVerificationMapper;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerification;
import br.com.shapeup.core.ports.output.verification.ResetPasswordVerificationOutputPort;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordVerificationAdapter implements ResetPasswordVerificationOutputPort {

    private final ResetPasswordVerificationRepository repository;

    @Override
    public void save(ResetPasswordVerification resetPasswordVerification) {
        log.info("Saving verification code for user {}", resetPasswordVerification.getEmail());
        repository.save(ResetPasswordVerificationMapper.toEntity(resetPasswordVerification));
    }
    @Override
    public ResetPasswordVerification findByUserEmail(String email, User user) {
        log.info("Finding verification code for user {}", email);
        return Try.of(() -> repository.findByEmail(email))
                .map(entity -> ResetPasswordVerificationMapper.toDomain(entity, user))
                .getOrElseThrow(() -> new RuntimeException("Code not found for user " + email));
    }

    @Override
    public void makeAuthorized(String email) {
        log.info("Verifying username for user {}", email);
        var verification = repository.findByEmail(email);
        verification.setVerified(true);

        Try.run(() -> repository.delete(verification))
                .onFailure(e -> log.error("Error deleting verification code for user {}", verification.getEmail(), e));
    }
}

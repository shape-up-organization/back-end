package br.com.shapeup.adapters.output.integration.verification;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.verification.EmailVerificationJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.verification.VerificationEmailMapper;
import br.com.shapeup.core.domain.verification.email.EmailVerification;
import br.com.shapeup.core.ports.output.verification.VerificationEmailOutputPort;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class VerificationEmailAdapter implements VerificationEmailOutputPort {

    private final EmailVerificationJpaRepository repository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void makeAuthorized(String email) {
        log.info("Verifying username for user {}", email);
        var emailVerification = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Code not found for user " + email));

        emailVerification.setVerified(true);
        emailVerification.setUpdatedAt(LocalDateTime.now());
        repository.save(emailVerification);

        var user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for email " + email));
        user.setActive(true);
        userJpaRepository.save(user);
    }

    @Override
    public Boolean isVerified(String email) {
        return repository.findVerifiedByEmail(email);
    }

    @Override
    public void delete(EmailVerification verification) {
        log.info("Deleting verification code for user {}", verification.getUsername());
        Try.run(() -> repository.delete(VerificationEmailMapper.toEntity(verification)))
                .onFailure(e -> log.error("Error deleting verification code for user {}", verification.getUsername(), e));
    }

    @Override
    public void deleteByEmail(String email) {
        log.info("Deleting verification code for user {}", email);
        var emailVerification = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Code not found for user " + email));
        repository.delete(emailVerification);
    }

    @Override
    public EmailVerification findByEmail(String email) {
        log.info("Finding verification code for user {}", email);
        return repository.findByEmail(email)
                .map(VerificationEmailMapper::toDomain)
                .orElse(null);
    }

    @Override
    public EmailVerification save(EmailVerification emailVerification) {
        log.info("Saving verification code for user {}", emailVerification.getUsername());
        return Try.of(() -> repository.save(VerificationEmailMapper.toEntity(emailVerification)))
                .map(VerificationEmailMapper::toDomain)
                .getOrElseThrow(
                        throwable -> new IllegalArgumentException("Error saving verification code for user " + emailVerification.getUsername(), throwable)
                );
    }
}

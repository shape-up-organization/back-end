package br.com.shapeup.adapters.output.repository.mapper.verification;

import br.com.shapeup.adapters.output.repository.model.verification.EmailVerificationEntity;
import br.com.shapeup.core.domain.verification.email.EmailVerification;
import br.com.shapeup.core.domain.verification.email.EmailVerificationId;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VerificationEmailMapper {

    public static EmailVerification toDomain(EmailVerificationEntity emailVerificationEntity) {
        return new EmailVerification(
                EmailVerificationId.from(emailVerificationEntity.getId()),
                emailVerificationEntity.getCode(),
                emailVerificationEntity.getVerified(),
                emailVerificationEntity.getEmail(),
                emailVerificationEntity.getUsername(),
                emailVerificationEntity.getCreatedAt(),
                emailVerificationEntity.getExpiresIn()
        );
    }

    public static EmailVerificationEntity toEntity(EmailVerification emailVerification) {
        return EmailVerificationEntity.builder()
                .id(emailVerification.getId().getValue())
                .code(emailVerification.getCode())
                .verified(emailVerification.getVerified())
                .email(emailVerification.getEmail())
                .username(emailVerification.getUsername())
                .createdAt(emailVerification.getCreatedAt())
                .expiresIn(emailVerification.getExpiresIn())
                .build();
    }
}

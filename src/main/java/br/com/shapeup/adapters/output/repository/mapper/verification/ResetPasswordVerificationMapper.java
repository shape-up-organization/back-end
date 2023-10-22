package br.com.shapeup.adapters.output.repository.mapper.verification;

import br.com.shapeup.adapters.output.repository.model.verification.ResetPasswordVerificationEntity;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerification;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerificationId;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordVerificationMapper {
    public static ResetPasswordVerificationEntity toEntity(ResetPasswordVerification resetPasswordVerification) {
        return ResetPasswordVerificationEntity.builder()
                .id(resetPasswordVerification.getId().getValue())
                .userId(resetPasswordVerification.getUser().getId().getValue())
                .status(resetPasswordVerification.getStatus())
                .verified(resetPasswordVerification.getVerified())
                .email(resetPasswordVerification.getEmail())
                .createdAt(OffsetDateTime.from(resetPasswordVerification.getCreatedAt()))
                .expiresIn(OffsetDateTime.from(resetPasswordVerification.getExpiresIn()))
                .build();
    }

    public static ResetPasswordVerification toDomain(ResetPasswordVerificationEntity resetPasswordVerificationEntity, User user) {
        return new ResetPasswordVerification(
                ResetPasswordVerificationId.from(resetPasswordVerificationEntity.getId()),
                resetPasswordVerificationEntity.getCode(),
                user,
                resetPasswordVerificationEntity.getStatus(),
                resetPasswordVerificationEntity.getVerified(),
                resetPasswordVerificationEntity.getEmail(),
                resetPasswordVerificationEntity.getCreatedAt().toLocalDateTime(),
                resetPasswordVerificationEntity.getExpiresIn().toLocalDateTime()
        );
    }
}

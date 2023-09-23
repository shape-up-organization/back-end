package br.com.shapeup.core.messages;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record SendCodeVerificationMessage(
        String id,
        String email,
        String code,
        String userName,
        OffsetDateTime createdAt
) {
}

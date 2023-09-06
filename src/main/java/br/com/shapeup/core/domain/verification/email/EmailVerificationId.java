package br.com.shapeup.core.domain.verification.email;

import br.com.shapeup.common.domain.Identifier;
import java.util.UUID;

public class EmailVerificationId extends Identifier<UUID> {

    protected EmailVerificationId(UUID value) {
        super(value);
    }

    public static EmailVerificationId from(String value) {
            return new EmailVerificationId(UUID.fromString(value));
    }

    public static EmailVerificationId from(UUID uuid) {
        return new EmailVerificationId(uuid);
    }
}

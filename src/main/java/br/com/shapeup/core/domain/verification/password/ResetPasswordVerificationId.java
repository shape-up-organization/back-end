package br.com.shapeup.core.domain.verification.password;

import br.com.shapeup.common.domain.Identifier;
import java.util.UUID;

public class ResetPasswordVerificationId extends Identifier<UUID> {
    protected ResetPasswordVerificationId(UUID value) {
        super(value);
    }

    public static ResetPasswordVerificationId from(String value) {
        return new ResetPasswordVerificationId(UUID.fromString(value));
    }

    public static ResetPasswordVerificationId from(UUID uuid) {
        return new ResetPasswordVerificationId(uuid);
    }

}

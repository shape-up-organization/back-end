package br.com.shapeup.core.usecase.verification;

import java.util.UUID;

public class VerificationCodeGenerator {

    public static String generate() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        Integer startIndex = uuidString.length() - 6;
        return uuidString.substring(startIndex);
    }
}

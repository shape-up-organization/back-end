package br.com.shapeup.core.usecase.verification;

public class VerificationCodeGenerator {

    public static String generate() {
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomNumber);
    }
}

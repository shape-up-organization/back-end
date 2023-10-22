package br.com.shapeup.core.usecase.verification;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.exception.InvalidCodeException;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerification;
import br.com.shapeup.core.messages.SendCodeVerificationMessage;
import br.com.shapeup.core.ports.input.verification.VerificationResetPasswordInput;
import br.com.shapeup.core.ports.output.verification.ResetPasswordVerificationOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.verification.SendCodeVerificationPublisherOutputPort;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class VerificationResetPasswordUsecase implements VerificationResetPasswordInput {

    private final FindUserOutput findUserOutput;
    private final SendCodeVerificationPublisherOutputPort sendCodeVerificationPublisherOutputPort;
    private final ResetPasswordVerificationOutputPort resetPasswordVerificationOutputPort;

    public VerificationResetPasswordUsecase(
            FindUserOutput findUserOutput,
            SendCodeVerificationPublisherOutputPort sendCodeVerificationPublisherOutputPort,
            ResetPasswordVerificationOutputPort resetPasswordVerificationOutputPort) {
        this.findUserOutput = findUserOutput;
        this.sendCodeVerificationPublisherOutputPort = sendCodeVerificationPublisherOutputPort;
        this.resetPasswordVerificationOutputPort = resetPasswordVerificationOutputPort;
    }

    @Override
    public void createVerificationCode(String email) {

        String codeGenerated = VerificationCodeGenerator.generate();
        User user = findUserOutput.findByEmail(email);
        ResetPasswordVerification resetPasswordVerification = resetPasswordVerificationOutputPort.findByUserEmail(email, user);
        boolean isExpired = resetPasswordVerification.getExpiresIn().isBefore(LocalDateTime.now());

        if (isExpired) {
            resetPasswordVerification.setCode(codeGenerated);
            resetPasswordVerification.setExpiresIn(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5));

            resetPasswordVerificationOutputPort.save(resetPasswordVerification);
            sendMessage(user, codeGenerated);
            return;
        }

        var newVerification = new ResetPasswordVerification(
                resetPasswordVerification.getId(),
                codeGenerated,
                user,
                "CREATED",
                false,
                email,
                LocalDateTime.now(ZoneId.of("UTC")),
                LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5)
        );
        resetPasswordVerificationOutputPort.save(newVerification);
        sendMessage(user, codeGenerated);
    }

    @Override
    public void confirmResetPassword(String email, String code) {
        User user = findUserOutput.findByEmail(email);
        ResetPasswordVerification verification = resetPasswordVerificationOutputPort.findByUserEmail(email, user);

        validateIfTheCodeHasExpired(verification);
        validateIfCodeVerificationIsSameInDatabase(code, verification);

        resetPasswordVerificationOutputPort.makeAuthorized(email);
    }

    @Override
    public Boolean isVerified(String email) {
        User user = findUserOutput.findByEmail(email);
        ResetPasswordVerification verification = resetPasswordVerificationOutputPort.findByUserEmail(email, user);
        return verification.getVerified();
    }

    private void sendMessage(User user, String codeGenerated) {
        var message = SendCodeVerificationMessage.builder()
                .id(UUID.randomUUID().toString())
                .email(user.getEmail().getValue())
                .code(codeGenerated)
                .userName(user.getFullName().getFirstName() + " " + user.getFullName().getLastName())
                .build();

        sendCodeVerificationPublisherOutputPort.sendResetPasswordVerification(message);
    }


    private void validateIfCodeVerificationIsSameInDatabase(String code, ResetPasswordVerification verification) {
        if (!verification.getCode().equals(code)) {
            throw new InvalidCodeException("Invalid reset password verification code");
        }
    }

    private void validateIfTheCodeHasExpired(ResetPasswordVerification verification) {
        if (verification.getExpiresIn().isBefore(LocalDateTime.now())) {
            throw new InvalidCodeException("Expired reset password verification code");
        }
    }
}

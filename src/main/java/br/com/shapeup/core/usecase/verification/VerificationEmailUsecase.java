package br.com.shapeup.core.usecase.verification;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.email.EmailVerification;
import br.com.shapeup.core.domain.verification.email.EmailVerificationId;
import br.com.shapeup.core.domain.verification.exception.InvalidCodeException;
import br.com.shapeup.core.messages.SendCodeVerificationMessage;
import br.com.shapeup.core.ports.input.verification.VerificationEmailInput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.verification.SendCodeVerificationPublisherOutputPort;
import br.com.shapeup.core.ports.output.verification.VerificationEmailOutputPort;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class VerificationEmailUsecase implements VerificationEmailInput {

    private final VerificationEmailOutputPort verificationEmailOutputPort;
    private final SendCodeVerificationPublisherOutputPort sendCodeVerificationPublisherOutputPort;
    private final FindUserOutput findUserOutput;

    public VerificationEmailUsecase(
            VerificationEmailOutputPort verificationEmailOutputPort,
            SendCodeVerificationPublisherOutputPort sendCodeVerificationPublisherOutputPort,
            FindUserOutput findUserOutput
    ) {
        this.verificationEmailOutputPort = verificationEmailOutputPort;
        this.sendCodeVerificationPublisherOutputPort = sendCodeVerificationPublisherOutputPort;
        this.findUserOutput = findUserOutput;
    }

    @Override
    public void confirmEmail(String email, String code) {

        EmailVerification verification = verificationEmailOutputPort.findByEmail(email);

        validateIfTheCodeHasExpired(verification);
        validateIfCodeVerificationIsSameInDatabase(code, verification);
        validateIfCodeAlreadyVerified(verification);

        verificationEmailOutputPort.makeAuthorized(email);
        sendCodeVerificationPublisherOutputPort.sendEmailVerification(
                SendCodeVerificationMessage.builder()
                        .email(email)
                        .code(code)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public void createVerificationCode(String email) {

        String codeGenerated = VerificationCodeGenerator.generate();
        EmailVerification verification = verificationEmailOutputPort.findByEmail(email);
        var user = findUserOutput.findByEmail(email);

        if(verification != null) {
            boolean isExpired = verification.getExpiresIn().isBefore(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5));

            if (isExpired) {
                verification.setCode(codeGenerated);
                verification.setExpiresIn(LocalDateTime.now().plusMinutes(5));

                verificationEmailOutputPort.save(verification);
                sendMessage(user, codeGenerated);
                return;
            }
        }

        var newVerification = new EmailVerification(
                EmailVerificationId.from(UUID.randomUUID()),
                codeGenerated,
                false,
                user.getEmail().getValue(),
                user.getUsername(),
                LocalDateTime.now(),
                LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5)
        );

        verificationEmailOutputPort.save(newVerification);

        sendMessage(user, codeGenerated);
    }


    @Override
    public Boolean isVerified(String email) {
        return verificationEmailOutputPort.isVerified(email);
    }

    private void sendMessage(User user, String codeGenerated) {
        sendCodeVerificationPublisherOutputPort.sendEmailVerification(
                SendCodeVerificationMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .email(user.getEmail().getValue())
                        .code(codeGenerated)
                        .userName(user.getFullName().getFirstName() + " " + user.getFullName().getLastName())
                        .build()
        );
    }

    private void validateIfCodeAlreadyVerified(EmailVerification verification) {
        if (verification.getVerified()) {
            throw new InvalidCodeException("E-mail already verified");
        }
    }

    private void validateIfCodeVerificationIsSameInDatabase(String code, EmailVerification verification) {
        if (!verification.getCode().equals(code)) {
            throw new InvalidCodeException("Invalid e-mail verification code");
        }
    }

    private void validateIfTheCodeHasExpired(EmailVerification verification) {
        if (verification.getExpiresIn().isBefore(LocalDateTime.now())) {
            throw new InvalidCodeException("Expired e-mail verification code");
        }
    }
}

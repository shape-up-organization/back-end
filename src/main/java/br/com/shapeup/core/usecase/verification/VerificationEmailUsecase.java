package br.com.shapeup.core.usecase.verification;

import br.com.shapeup.adapters.output.integration.email.EmailService;
import br.com.shapeup.adapters.output.repository.jpa.template.EmailTemplateRepository;
import br.com.shapeup.adapters.output.repository.model.template.HtmlTemplateType;
import br.com.shapeup.core.domain.verification.email.EmailVerification;
import br.com.shapeup.core.domain.verification.email.EmailVerificationId;
import br.com.shapeup.core.domain.verification.exception.InvalidCodeException;
import br.com.shapeup.core.ports.input.verification.VerificationEmailInput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.verification.VerificationEmailOutputPort;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class VerificationEmailUsecase implements VerificationEmailInput {

    private final VerificationEmailOutputPort verificationEmailOutputPort;
    private final FindUserOutput findUserOutput;
    private final EmailService emailService;
    private final EmailTemplateRepository emailTemplateRepository;

    public VerificationEmailUsecase(
            VerificationEmailOutputPort verificationEmailOutputPort,
            FindUserOutput findUserOutput,
            EmailService emailService,
            EmailTemplateRepository emailTemplateRepository
    ) {
        this.verificationEmailOutputPort = verificationEmailOutputPort;
        this.findUserOutput = findUserOutput;
        this.emailService = emailService;
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @Override
    public void confirmEmail(String email, String code) {

        EmailVerification verification = verificationEmailOutputPort.findByEmail(email);

        validateIfTheCodeHasExpired(verification);
        validateIfCodeVerificationIsSameInDatabase(code, verification);
        validateIfCodeAlreadyVerified(verification);

        verificationEmailOutputPort.makeAuthorized(email);
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
                var template = emailTemplateRepository.findByType(HtmlTemplateType.CONFIRM_EMAIL_CODE_VERIFICATION.getValue())
                        .orElseThrow()
                        .getContent();

                template = template.replace("{{code}}", codeGenerated);
                template = template.replace("{{user_name}}", user.getFullName().get());

                emailService.sendHtmlEmail(user.getEmail().getValue(), "Shape Up - Código de verificação", template);
                return;
            }
        } else {

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

            var template = emailTemplateRepository.findByType(HtmlTemplateType.CONFIRM_EMAIL_CODE_VERIFICATION.getValue())
                    .orElseThrow()
                    .getContent();

            template = template.replace("{{code}}", codeGenerated);
            template = template.replace("{{user_name}}", user.getFullName().get());

            emailService.sendHtmlEmail(user.getEmail().getValue(), "Shape Up - Código de verificação", template);
        }
    }


    @Override
    public Boolean isVerified(String email) {
        return verificationEmailOutputPort.isVerified(email);
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

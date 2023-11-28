package br.com.shapeup.core.usecase.verification;

import br.com.shapeup.adapters.output.integration.email.EmailService;
import br.com.shapeup.adapters.output.repository.jpa.template.EmailTemplateRepository;
import br.com.shapeup.adapters.output.repository.model.template.HtmlTemplateType;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.verification.exception.InvalidCodeException;
import br.com.shapeup.core.domain.verification.password.ResetPasswordVerification;
import br.com.shapeup.core.ports.input.verification.VerificationResetPasswordInput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.verification.ResetPasswordVerificationOutputPort;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VerificationResetPasswordUsecase implements VerificationResetPasswordInput {

    private final FindUserOutput findUserOutput;
    private final ResetPasswordVerificationOutputPort resetPasswordVerificationOutputPort;
    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailService emailService;

    public VerificationResetPasswordUsecase(
            FindUserOutput findUserOutput,
            ResetPasswordVerificationOutputPort resetPasswordVerificationOutputPort,
            EmailTemplateRepository emailTemplateRepository,
            EmailService emailService) {
        this.findUserOutput = findUserOutput;
        this.resetPasswordVerificationOutputPort = resetPasswordVerificationOutputPort;
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailService = emailService;
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
            var template = emailTemplateRepository.findByType(HtmlTemplateType.CONFIRM_EMAIL_CODE_VERIFICATION.getValue())
                    .orElseThrow()
                    .getContent();

            template = template.replace("{{code}}", codeGenerated);
            template = template.replace("{{user_name}}", user.getFullName().get());

            emailService.sendHtmlEmail(user.getEmail().getValue(), "Shape Up - Código de verificação", template );
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
        var template = emailTemplateRepository.findByType(HtmlTemplateType.CONFIRM_EMAIL_CODE_VERIFICATION.getValue())
                .orElseThrow()
                .getContent();

        template.replace("{{code}}", codeGenerated);
        template.replace("{{user_name}}", user.getFullName().get());

        emailService.sendHtmlEmail(user.getEmail().getValue(), "Shape Up - Código de verificação", template );
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

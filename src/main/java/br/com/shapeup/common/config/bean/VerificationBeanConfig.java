package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.email.EmailService;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.integration.verification.VerificationEmailAdapter;
import br.com.shapeup.adapters.output.repository.jpa.template.EmailTemplateRepository;
import br.com.shapeup.core.ports.output.verification.ResetPasswordVerificationOutputPort;
import br.com.shapeup.core.usecase.verification.VerificationEmailUsecase;
import br.com.shapeup.core.usecase.verification.VerificationResetPasswordUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificationBeanConfig {

    @Bean
    public VerificationEmailUsecase verificationUsecase(
            VerificationEmailAdapter verificationEmailAdapter,
            FindUserAdapter findUserAdapter,
            EmailService emailService,
            EmailTemplateRepository emailTemplateRepository
    ) {
        return new VerificationEmailUsecase(verificationEmailAdapter, findUserAdapter, emailService, emailTemplateRepository);
    }

    @Bean
    public VerificationResetPasswordUsecase verificationResetPasswordUsecase(
            FindUserAdapter findUserAdapter,
            ResetPasswordVerificationOutputPort resetPasswordVerificationOutputPort,
            EmailTemplateRepository emailTemplateRepository,
            EmailService emailService
    ) {
        return new VerificationResetPasswordUsecase(findUserAdapter, resetPasswordVerificationOutputPort, emailTemplateRepository, emailService);
    }
}

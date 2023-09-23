package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.kafka.SendCodeVerificationPublisherAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.integration.verification.ResetPasswordVerificationAdapter;
import br.com.shapeup.adapters.output.integration.verification.VerificationEmailAdapter;
import br.com.shapeup.core.usecase.verification.VerificationEmailUsecase;
import br.com.shapeup.core.usecase.verification.VerificationResetPasswordUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificationBeanConfig {

    @Bean
    public VerificationEmailUsecase verificationUsecase(
            VerificationEmailAdapter verificationEmailAdapter,
            SendCodeVerificationPublisherAdapter sendCodeVerificationPublisherAdapter,
            FindUserAdapter findUserAdapter
    ) {
        return new VerificationEmailUsecase(verificationEmailAdapter, sendCodeVerificationPublisherAdapter, findUserAdapter);
    }

    @Bean
    public VerificationResetPasswordUsecase verificationResetPasswordUsecase(
            FindUserAdapter findUserAdapter,
            SendCodeVerificationPublisherAdapter sendCodeVerificationPublisherAdapter,
            ResetPasswordVerificationAdapter resetPasswordVerificationAdapter
    ) {
        return new VerificationResetPasswordUsecase(findUserAdapter, sendCodeVerificationPublisherAdapter, resetPasswordVerificationAdapter);
    }
}

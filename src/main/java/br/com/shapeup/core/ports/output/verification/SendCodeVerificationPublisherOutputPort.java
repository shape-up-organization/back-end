package br.com.shapeup.core.ports.output.verification;

import br.com.shapeup.core.messages.SendCodeVerificationMessage;

public interface SendCodeVerificationPublisherOutputPort {
    void sendEmailVerification(SendCodeVerificationMessage message);
    void sendResetPasswordVerification(SendCodeVerificationMessage message);
}

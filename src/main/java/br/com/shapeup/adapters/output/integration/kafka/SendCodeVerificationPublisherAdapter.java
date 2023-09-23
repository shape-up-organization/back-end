package br.com.shapeup.adapters.output.integration.kafka;

import static br.com.shapeup.common.config.kafka.Topic.CONFIRM_EMAIL_TOPIC;
import static br.com.shapeup.common.config.kafka.Topic.RESET_PASSWORD_TOPIC;
import br.com.shapeup.common.config.kafka.serdes.GsonBuilderSingletonEnum;
import br.com.shapeup.core.messages.SendCodeVerificationMessage;
import br.com.shapeup.core.ports.output.verification.SendCodeVerificationPublisherOutputPort;
import com.google.gson.Gson;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SendCodeVerificationPublisherAdapter implements SendCodeVerificationPublisherOutputPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = GsonBuilderSingletonEnum.INSTANCE.getGsonBuilder().create();

    @Override
    @KafkaListener(topics = "tp-send-email-code-verification", groupId = "gp-send-email-code-verification")
    public void sendEmailVerification(SendCodeVerificationMessage message) {
        var messageJson = gson.toJson(message);
        var topic = CONFIRM_EMAIL_TOPIC.getName();

        log.info("Sending message to topic {} with message {}", topic, messageJson);
        Try.run(() -> kafkaTemplate.send(topic, messageJson))
                .onSuccess(result ->
                        log.info(
                                "Message sent to topic {} with message {}",
                                topic,
                                messageJson
                        )
                )
                .onFailure(exception ->
                        log.error(
                                "Error sending message to topic {} with message {}",
                                topic,
                                messageJson,
                                exception
                        )
                );
    }

    @Override
    @KafkaListener(topics = "tp-send-reset-password-code-verification", groupId = "gp-send-reset-password-code-verification")
    public void sendResetPasswordVerification(SendCodeVerificationMessage message) {
        var messageJson = gson.toJson(message);
        var topic = RESET_PASSWORD_TOPIC.getName();

        log.info("Sending message to topic {} with message {}", topic, messageJson);
        Try.run(() -> kafkaTemplate.send(topic, messageJson))
                .onSuccess(result ->
                        log.info(
                                "Message sent to topic {} with message {}",
                                topic,
                                messageJson
                        )
                )
                .onFailure(exeption ->
                        log.error(
                                "Error sending message to topic {} with message {}",
                                topic,
                                messageJson,
                                exeption
                        )
                );
    }
}

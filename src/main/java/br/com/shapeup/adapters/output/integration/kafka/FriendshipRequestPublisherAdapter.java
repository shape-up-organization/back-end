package br.com.shapeup.adapters.output.integration.kafka;

import br.com.shapeup.common.config.kafka.Topic;
import br.com.shapeup.common.config.kafka.serdes.GsonBuilderSingletonEnum;
import br.com.shapeup.core.messages.FriendshipRequestMessage;
import br.com.shapeup.core.ports.output.friend.FriendshipRequestPublisherOutputPort;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendshipRequestPublisherAdapter implements FriendshipRequestPublisherOutputPort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @KafkaListener(topics = "tp-friendship-request", groupId = "gp-friendship-request")
    public void send(FriendshipRequestMessage message) {

        GsonBuilder builder = GsonBuilderSingletonEnum.INSTANCE.getGsonBuilder();
        Gson gson = builder.create();
        var messageJson = gson.toJson(message);

        kafkaTemplate.send(Topic.FRIENDSHIP_REQUEST.getName(), messageJson);
    }
}

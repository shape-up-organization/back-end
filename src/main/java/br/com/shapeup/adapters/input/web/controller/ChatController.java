package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.chat.ChatMapper;
import br.com.shapeup.adapters.output.integration.chat.ChatGateway;
import br.com.shapeup.adapters.output.repository.model.chat.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatGateway chatGateway;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/private-message")
    @SendToUser("/private")
    public Message recMessage(@Payload Message message, @DestinationVariable String username) {
        simpMessagingTemplate.convertAndSendToUser(username, "/private", message);

        chatGateway.saveMessageToDatabase(message);

        return message;
    }
}

package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.output.repository.model.chat.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public MessageEntity receiveMessage(@Payload MessageEntity messageEntity){
        return messageEntity;
    }

    @MessageMapping("/private-message")
    public MessageEntity sendMessageToFollowers(@Payload MessageEntity messageEntity){
        simpMessagingTemplate.convertAndSendToUser(messageEntity.getReceiverName(),"/private", messageEntity);
        return messageEntity;
    }
}

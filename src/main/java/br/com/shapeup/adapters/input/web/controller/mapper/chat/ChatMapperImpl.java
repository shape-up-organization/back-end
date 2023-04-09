package br.com.shapeup.adapters.input.web.controller.mapper.chat;

import br.com.shapeup.adapters.output.repository.model.chat.ChatDocument;
import br.com.shapeup.adapters.output.repository.model.chat.Message;
import org.springframework.stereotype.Component;

@Component
public class ChatMapperImpl implements ChatMapper{
    @Override
    public ChatDocument messageToChatDocument(Message message) {
        return ChatDocument.builder()
                .build();
    }
}

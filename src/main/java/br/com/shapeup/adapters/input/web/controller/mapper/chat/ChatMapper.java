package br.com.shapeup.adapters.input.web.controller.mapper.chat;

import br.com.shapeup.adapters.output.repository.model.chat.ChatDocument;
import br.com.shapeup.adapters.output.repository.model.chat.Message;

public interface ChatMapper {
    ChatDocument messageToChatDocument(Message message);
}

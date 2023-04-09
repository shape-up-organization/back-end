package br.com.shapeup.adapters.output.integration.chat;

import br.com.shapeup.adapters.output.repository.model.chat.Message;

public interface ChatGateway {

    void saveMessageToDatabase(Message message);

}

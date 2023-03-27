package br.com.shapeup.adapters.output.integration.chat;

import br.com.shapeup.adapters.output.repository.jpa.chat.ChatRepository;
import br.com.shapeup.core.ports.output.chat.ChatValidationsOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatValidationsAdapter implements ChatValidationsOutput {
    private final ChatRepository chatRepository;

    @Override
    public Integer countUsersInChat(String chatId) {
//        Integer countUsers = chatRepository.countUsersInChat(chatId);
        return null;
    }
}

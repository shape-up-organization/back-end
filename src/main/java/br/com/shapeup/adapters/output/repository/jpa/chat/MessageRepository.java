package br.com.shapeup.adapters.output.repository.jpa.chat;

import br.com.shapeup.adapters.output.repository.model.chat.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}

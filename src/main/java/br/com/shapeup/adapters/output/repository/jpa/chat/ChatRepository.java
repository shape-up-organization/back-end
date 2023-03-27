package br.com.shapeup.adapters.output.repository.jpa.chat;

import br.com.shapeup.adapters.output.repository.model.chat.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
//    @Query("SELECT COUNT(u) FROM UserEntity u JOIN u.friends c WHERE c.id = :chatId")
//    Long countUsersInChat(@Param("chatId") Long chatId);
}

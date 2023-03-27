package br.com.shapeup.core.domain.chat;

import br.com.shapeup.adapters.output.repository.model.chat.MessageEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.handler.ThrowsValidationHandler;
import java.util.List;
import java.util.Set;

public class Chat extends Entity<ChatId> {

    private String name;

    private Set<UserEntity> users;

    private List<MessageEntity> messageEntities;

    public Chat(String name, Set<UserEntity> users) {
        super(ChatId.unique(1L));
        this.name = name;
        this.users = users;
    }

    public static Chat newChat(String name, Set<UserEntity> users) {
        var id = ChatId.unique(1L);
        return new Chat(name, users);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ChatValidator(handler, this).validate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }
}

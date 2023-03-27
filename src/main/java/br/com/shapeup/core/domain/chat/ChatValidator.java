package br.com.shapeup.core.domain.chat;

import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class ChatValidator extends Validator {

    private final Chat chat;
    protected ChatValidator(ValidationHandler anHandler, Chat chat) {
        super(anHandler);
        this.chat = chat;
    }

    @Override
    public void validate() {
        validateUsersCannotBeNull();
        validateUsersCannotRepeat();
        validateAmountUsersInChat();
    }

    private void validateAmountUsersInChat() {
        if (chat.getUsers().size() < 2) {
            // TODO Criar exceção para chat com menos de 2 usuários
            throw new IllegalArgumentException("Chat must have at least 2 users");
        }
    }

    private void validateUsersCannotBeNull() {
        chat.getUsers().forEach(user -> {
            if (user == null) {
                // TODO Criar exceção para usuário nulo
                throw new IllegalArgumentException("User cannot be null");
            }
        });
    }

    private void validateUsersCannotRepeat() {
        // TODO Criar exceção para usuário repetido
        chat.getUsers().forEach(user -> {
            if (chat.getUsers().stream().filter(u -> u.equals(user)).count() > 1) {
                throw new IllegalArgumentException("User cannot repeat");
            }
        });
    }
}

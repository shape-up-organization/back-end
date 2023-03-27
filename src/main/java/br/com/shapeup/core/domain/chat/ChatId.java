package br.com.shapeup.core.domain.chat;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;

public class ChatId extends Identifier {
    private final Long value;

    public ChatId(Long value) {
        super(value);
        Objects.requireNonNull(value, "ChatId cannot be null");
        this.value = value;
    }

    public static ChatId unique(Long value) {
        return new ChatId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChatId chatId = (ChatId) o;
        return getValue().equals(chatId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }
}

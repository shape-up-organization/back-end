package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class AnswerCommentId extends Identifier<String> {
    private final String value;

    private AnswerCommentId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static AnswerCommentId unique() {
        return AnswerCommentId.from(UUID.randomUUID());
    }

    public static AnswerCommentId from(final String anId) {
        return new AnswerCommentId(anId);
    }

    public static AnswerCommentId from(final UUID anId) {
        return new AnswerCommentId(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AnswerCommentId answerCommentId = (AnswerCommentId) o;
        return getValue().equals(answerCommentId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

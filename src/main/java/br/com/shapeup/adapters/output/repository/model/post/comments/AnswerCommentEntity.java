package br.com.shapeup.adapters.output.repository.model.post.comments;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;
import java.util.UUID;

@Document("tb_answer_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCommentEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String commentMessage;

    private String idComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerCommentEntity that = (AnswerCommentEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

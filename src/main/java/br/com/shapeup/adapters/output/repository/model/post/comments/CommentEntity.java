package br.com.shapeup.adapters.output.repository.model.post.comments;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tb_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String commentMessage;

    private String userId;

    private String idPost;

    private LocalDateTime createdAt;

    public CommentEntity(String commentMessage, String userId, String idPost) {
        this.commentMessage = commentMessage;
        this.userId = userId;
        this.idPost = idPost;
        this.createdAt = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

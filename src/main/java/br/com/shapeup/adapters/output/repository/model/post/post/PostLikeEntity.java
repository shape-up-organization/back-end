package br.com.shapeup.adapters.output.repository.model.post.post;

import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tb_like_post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String userId;

    private String postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLikeEntity that = (PostLikeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package br.com.shapeup.adapters.output.repository.model.post.post;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tb_photo_post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPhotoEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String photoUrl;

    private String idPost;

    private LocalDateTime created_at;

    public PostPhotoEntity(String photoUrl, String idPost) {
        this.photoUrl = photoUrl;
        this.idPost = idPost;
        created_at = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostPhotoEntity that = (PostPhotoEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

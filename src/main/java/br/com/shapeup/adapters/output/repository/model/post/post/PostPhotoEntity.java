package br.com.shapeup.adapters.output.repository.model.post.post;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document("tb_photo_post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPhotoEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String photoUrlPhoto;

    private String idPost;

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

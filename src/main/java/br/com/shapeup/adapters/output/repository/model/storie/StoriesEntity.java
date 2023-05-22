package br.com.shapeup.adapters.output.repository.model.storie;

import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tb_stories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoriesEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String pictureStorieUrl;

    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoriesEntity that = (StoriesEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

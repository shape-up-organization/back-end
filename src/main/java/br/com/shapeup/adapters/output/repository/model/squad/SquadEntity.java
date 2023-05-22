package br.com.shapeup.adapters.output.repository.model.squad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_squad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SquadEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String name;

    @Column(columnDefinition = "BIT", nullable = false)
    private boolean isActive;

    @Column
    private int xp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadEntity that = (SquadEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

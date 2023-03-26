package br.com.shapeup.adapters.output.repository.model.squad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

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

    @Column
    private String isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadEntity that = (SquadEntity) o;
        return getId() != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

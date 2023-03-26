package br.com.shapeup.adapters.output.repository.model.level;

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
@Table(name = "tb_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LevelEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column
    private double min;

    @Column
    private double max;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelEntity that = (LevelEntity) o;
        return Double.compare(that.min, min) == 0 && Double.compare(that.max, max) == 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, min, max);
    }
}

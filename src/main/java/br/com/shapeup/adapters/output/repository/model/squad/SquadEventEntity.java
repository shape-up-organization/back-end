package br.com.shapeup.adapters.output.repository.model.squad;

import br.com.shapeup.adapters.output.repository.model.common.AddressEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_squad_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SquadEventEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String name;

    @Column
    private LocalDateTime date;

    @Column(length = 1000)
    private String description;

    @Column
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_squad_id", referencedColumnName = "id")
    private SquadEntity squadEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "id")
    private AddressEntity addressEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadEventEntity that = (SquadEventEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

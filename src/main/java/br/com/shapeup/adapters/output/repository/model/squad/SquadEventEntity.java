package br.com.shapeup.adapters.output.repository.model.squad;

import br.com.shapeup.adapters.output.repository.model.common.AddressEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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

    @Column
    private String description;

    @Column
    private String fkSquadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_squad_id", referencedColumnName = "id")
    private SquadEntity squadEntity;

    @Column
    private String fkAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "id")
    private AddressEntity addressEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadEventEntity that = (SquadEventEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(fkSquadId, that.fkSquadId) && Objects.equals(squadEntity, that.squadEntity) && Objects.equals(fkAddressId, that.fkAddressId) && Objects.equals(addressEntity, that.addressEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, description, fkSquadId, squadEntity, fkAddressId, addressEntity);
    }
}

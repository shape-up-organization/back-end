package br.com.shapeup.adapters.output.repository.model.event;

import br.com.shapeup.adapters.output.repository.model.common.AddressEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
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
@Table(name = "tb_public_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicEventEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column
    private String name;

    @Column
    private LocalDateTime date;

    @Column
    private String description;

    @Column
    private int xp;

    @Column
    private String tag;

    @Column
    private String fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column
    private String fkAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "id")
    private AddressEntity addressEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicEventEntity that = (PublicEventEntity) o;
        return xp == that.xp && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity) && Objects.equals(fkAddressId, that.fkAddressId) && Objects.equals(addressEntity, that.addressEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, description, xp, fkUserId, userEntity, fkAddressId, addressEntity);
    }
}

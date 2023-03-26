package br.com.shapeup.adapters.output.repository.model.squad;

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

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_squad_members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SquadMembersEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String position;

    @Column(name = "fk_squad_id", insertable = false, updatable = false)
    private UUID fkSquadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_squad_id", referencedColumnName = "id")
    private SquadEntity squadEntity;

    @Column(name = "fk_user_id", insertable = false, updatable = false)
    private UUID fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadMembersEntity that = (SquadMembersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(position, that.position) && Objects.equals(fkSquadId, that.fkSquadId) && Objects.equals(squadEntity, that.squadEntity) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, fkSquadId, squadEntity, fkUserId, userEntity);
    }
}

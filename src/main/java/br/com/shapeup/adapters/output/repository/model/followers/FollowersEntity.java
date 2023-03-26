package br.com.shapeup.adapters.output.repository.model.followers;

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
@Table(name = "tb_followers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowersEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "fk_user_id", insertable = false, updatable = false)
    private UUID fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(name = "fk_follower_id", insertable = false, updatable = false)
    private UUID fkFollowerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_follower_id", referencedColumnName = "id")
    private UserEntity userFollowerEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowersEntity that = (FollowersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity) && Objects.equals(fkFollowerId, that.fkFollowerId) && Objects.equals(userFollowerEntity, that.userFollowerEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkUserId, userEntity, fkFollowerId, userFollowerEntity);
    }
}

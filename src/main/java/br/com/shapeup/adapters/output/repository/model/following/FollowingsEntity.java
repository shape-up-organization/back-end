package br.com.shapeup.adapters.output.repository.model.following;

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
@Table(name = "tb_followings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowingsEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column
    private String fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column
    private String fkFollowingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_following_id", referencedColumnName = "id")
    private UserEntity userFollowingEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowingsEntity that = (FollowingsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity) && Objects.equals(fkFollowingId, that.fkFollowingId) && Objects.equals(userFollowingEntity, that.userFollowingEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkUserId, userEntity, fkFollowingId, userFollowingEntity);
    }
}

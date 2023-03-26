package br.com.shapeup.adapters.output.repository.model.user;

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
@Table(name = "tb_user_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private String senderUserPhotoUrl;

    @Column(name = "fk_user_id", insertable = false, updatable = false)
    private UUID fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotificationEntity that = (UserNotificationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(message, that.message) && Objects.equals(senderUserPhotoUrl, that.senderUserPhotoUrl) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message, senderUserPhotoUrl, fkUserId, userEntity);
    }
}

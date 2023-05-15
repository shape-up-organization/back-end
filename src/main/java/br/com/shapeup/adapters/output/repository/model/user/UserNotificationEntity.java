package br.com.shapeup.adapters.output.repository.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String type;

    @Column
    private UUID idType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_sender_id", referencedColumnName = "user_id")
    private UserEntity userSenderEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotificationEntity that = (UserNotificationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

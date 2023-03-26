package br.com.shapeup.adapters.output.repository.model.challenges;

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
@Table(name = "tb_personal_challenges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalChallengesEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String tag;

    @Column(columnDefinition = "BIT", nullable = false)
    private boolean isDone;

    @Column
    private String fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalChallengesEntity that = (PersonalChallengesEntity) o;
        return isDone == that.isDone && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(tag, that.tag) && Objects.equals(fkUserId, that.fkUserId) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tag, isDone, fkUserId, userEntity);
    }
}

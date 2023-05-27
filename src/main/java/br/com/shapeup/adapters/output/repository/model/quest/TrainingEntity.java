package br.com.shapeup.adapters.output.repository.model.quest;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_training")
public class TrainingEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "training_id")
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private Long xp;
    private Integer duration;
    private String description;
    private String classification;
    private Long unlockXp;

    @ManyToMany(mappedBy = "trainings")
    private List<UserEntity> users;

    @OneToMany(
            mappedBy = "training",
            fetch = FetchType.EAGER
    )
    private List<ExerciseEntity> exercises;
}

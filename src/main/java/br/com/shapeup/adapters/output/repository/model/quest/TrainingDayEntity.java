package br.com.shapeup.adapters.output.repository.model.quest;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_training_days")
public class TrainingDayEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingEntity training;
    private String dayOfWeek;
    private String period;
    private String status;
}

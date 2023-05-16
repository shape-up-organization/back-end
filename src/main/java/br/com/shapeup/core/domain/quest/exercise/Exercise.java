package br.com.shapeup.core.domain.quest.exercise;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class Exercise extends Entity<ExerciseId> {

    private String exercise;
    private Integer duration;

    Exercise(
            ExerciseId exerciseId,
            String exercise,
            Integer duration
    ) {
        super(exerciseId);
        this.exercise = exercise;
        this.duration = duration;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ExerciseValidator(handler, this).validate();
    }

    public static Exercise create(
            ExerciseId exerciseId,
            String exercise,
            Integer duration
    ) {
        return new ExerciseBuilder()
                .setExerciseId(exerciseId)
                .setExercise(exercise)
                .setDuration(duration)
                .create();
    }

    public String getExercise() {
        return exercise;
    }

    public Integer getDuration() {
        return duration;
    }
}

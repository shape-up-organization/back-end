package br.com.shapeup.core.domain.quest.exercise;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class Exercise extends Entity<ExerciseId> {

    private String exercise;

    Exercise(
            ExerciseId exerciseId,
            String exercise
    ) {
        super(exerciseId);
        this.exercise = exercise;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ExerciseValidator(handler, this).validate();
    }

    public static Exercise create(
            ExerciseId exerciseId,
            String exercise
    ) {
        return new ExerciseBuilder()
                .setExerciseId(exerciseId)
                .setExercise(exercise)
                .create();
    }

    public String getExercise() {
        return exercise;
    }
}

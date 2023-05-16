package br.com.shapeup.core.domain.quest.exercise;

public class ExerciseBuilder {
    private ExerciseId exerciseId;
    private String exercise;
    private Integer duration;

    public ExerciseBuilder setExerciseId(ExerciseId exerciseId) {
        this.exerciseId = exerciseId;
        return this;
    }

    public ExerciseBuilder setExercise(String exercise) {
        this.exercise = exercise;
        return this;
    }

    public ExerciseBuilder setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Exercise create() {
        return new Exercise(exerciseId, exercise, duration);
    }
}
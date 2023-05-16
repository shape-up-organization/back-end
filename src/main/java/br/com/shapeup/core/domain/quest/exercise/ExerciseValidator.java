package br.com.shapeup.core.domain.quest.exercise;

import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class ExerciseValidator extends Validator {

    private final Exercise exercise;

    protected ExerciseValidator(ValidationHandler anHandler, Exercise exercise) {
        super(anHandler);
        this.exercise = exercise;
    }

    @Override
    public void validate() {
    }
}


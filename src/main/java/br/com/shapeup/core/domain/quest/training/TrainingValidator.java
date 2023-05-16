package br.com.shapeup.core.domain.quest.training;

import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class TrainingValidator extends Validator {

    private final Training training;

    protected TrainingValidator(ValidationHandler anHandler, Training training) {
        super(anHandler);
        this.training = training;
    }

    @Override
    public void validate() {
    }
}

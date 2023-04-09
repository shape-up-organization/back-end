package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.exceptions.squad.InvalidSquadException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class SquadValidator extends Validator {
    private final Squad squad;

    protected SquadValidator(ValidationHandler anHandler, Squad squad) {
        super(anHandler);
        this.squad = squad;
    }

    @Override
    public void validate() {
        validateSquad();
    }

    public void validateSquad() {
        checkSquadConstraints();
    }

    private void checkSquadConstraints() {
        String name = this.squad.getName();
        if (name == null || name.isBlank()) {
            throw new InvalidSquadException("'name' should not be null");
        }
    }
}

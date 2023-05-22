package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.exceptions.squad.InvalidSquadEventException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;
import java.time.LocalDateTime;

public class SquadEventValidator extends Validator {
    private final SquadEvent squadEvent;

    protected SquadEventValidator(ValidationHandler anHandler, SquadEvent squadEvent) {
        super(anHandler);
        this.squadEvent = squadEvent;
    }

    @Override
    public void validate() {
        validateSquadEvent();
    }

    public void validateSquadEvent() {
        checkSquadEventConstraints();
    }

    private void checkSquadEventConstraints() {
        String idSquad = this.squadEvent.getIdSquad();
        if (idSquad == null || idSquad.isBlank()) {
            throw new InvalidSquadEventException("'idSquad' should not be null");
        }

        String name = this.squadEvent.getName();
        if (name == null || name.isBlank()) {
            throw new InvalidSquadEventException("'name' should not be null");
        }

        LocalDateTime date = this.squadEvent.getDate();
        if (date == null) {
            throw new InvalidSquadEventException("'date' should not be null");
        }

        String address = this.squadEvent.getIdAddress();
        if (address == null || address.isBlank()) {
            throw new InvalidSquadEventException("'address' should not be null");
        }
    }
}

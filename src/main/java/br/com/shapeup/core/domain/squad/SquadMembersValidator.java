package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.exceptions.squad.InvalidSquadMembersException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class SquadMembersValidator extends Validator {
    private final SquadMembers squadMembers;

    protected SquadMembersValidator(ValidationHandler anHandler, SquadMembers squadMembers) {
        super(anHandler);
        this.squadMembers = squadMembers;
    }

    @Override
    public void validate() {
        validateSquadMembers();
    }

    public void validateSquadMembers() {
        checkSquadMembersConstraints();
    }

    private void checkSquadMembersConstraints() {
        String idSquad = this.squadMembers.getIdSquad();
        if (idSquad == null || idSquad.isBlank()) {
            throw new InvalidSquadMembersException("'idSquad' should not be null");
        }

        String idUser = this.squadMembers.getIdUser();
        if (idUser == null || idUser.isBlank()) {
            throw new InvalidSquadMembersException("'idUser' should not be null");
        }
    }
}

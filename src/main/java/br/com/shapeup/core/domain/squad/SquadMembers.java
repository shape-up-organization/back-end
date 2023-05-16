package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class SquadMembers extends Entity<SquadMembersId> {
    private String idSquad;

    private String idUser;

    private String position;

    private SquadMembers(SquadMembersId id, String position, String idSquad, String idUser) {
        super(id);
        this.position = position;
        this.idSquad = idSquad;
        this.idUser = idUser;
    }

    public static SquadMembers newSquadMembers(SquadMembersId id,  String position, String idSquad, String idUser){
        return new SquadMembers(id, position, idSquad, idUser);
    }
    @Override
    public void validate(ValidationHandler handler) {
        new SquadMembersValidator(handler, this).validate();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIdSquad() {
        return idSquad;
    }

    public void setIdSquad(String idSquad) {
        this.idSquad = idSquad;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SquadMembers that = (SquadMembers) o;

        return Objects.equals(idSquad, that.idSquad) &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSquad, idUser, position);
    }
}

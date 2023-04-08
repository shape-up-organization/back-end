package br.com.shapeup.core.domain.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadMembersEntity;
import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class SquadMembers extends Entity<SquadMembersId> {
    private String position;

    public SquadMembers(String position) {
        super(SquadMembersId.unique());
        this.position = position;
    }

    public static SquadMembers newSquadMembers(String position){
        return newSquadMembers(position);
    }
    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SquadMembers that = (SquadMembers) o;

        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}

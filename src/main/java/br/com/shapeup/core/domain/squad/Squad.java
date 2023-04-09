package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class Squad extends Entity<SquadId> {

    private String name;

    private boolean isActive;

    private int xp;

    private Squad(SquadId id, String name, boolean isActive, int xp) {
        super(id);
        this.name = name;
        this.isActive = isActive;
        this.xp = xp;
    }

    public static Squad newSquad(SquadId id,String name, boolean isActive, int xp){
        return new Squad(id, name,isActive,xp);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new SquadValidator(handler, this).validate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Squad squad = (Squad) o;

        return isActive == squad.isActive &&
                xp == squad.xp &&
                Objects.equals(name, squad.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, isActive, xp);
    }
}

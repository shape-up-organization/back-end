package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class Squad extends Entity<SquadId> {

    private String name;

    private boolean isActive;
    private int xp;

    public Squad(String name, boolean isActive, int xp) {
        super(SquadId.unique());
        this.name = name;
        this.isActive = isActive;
        this.xp = xp;
    }

    public static Squad newSquad(String name, boolean isActive, int xp){
        return new Squad(name,isActive,xp);
    }

    @Override
    public void validate(ValidationHandler handler) {

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Squad squad = (Squad) o;

        if (isActive != squad.isActive) return false;
        if (xp != squad.xp) return false;
        return Objects.equals(name, squad.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + xp;
        return result;
    }
}

package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.time.LocalDateTime;
import java.util.Objects;

public class SquadEvent extends Entity<SquadEventId> {
    private String idSquad;

    private String name;

    private LocalDateTime date;

    private String description;

    private String tag;

    private String idAddress;

    private SquadEvent(SquadEventId id, String name, LocalDateTime date, String description, String tag, String idSquad, String idAddress) {
        super(id);
        this.name = name;
        this.date = date;
        this.description = description;
        this.tag = tag;
        this.idSquad = idSquad;
        this.idAddress = idAddress;
    }

    public static SquadEvent newSquadEvent(SquadEventId id, String name,LocalDateTime date,String description,String tag,
                                           String idSquad, String idAddress){
        return new SquadEvent(id, name,date,description,tag,idSquad, idAddress);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new SquadEventValidator(handler, this).validate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIdSquad() {
        return idSquad;
    }

    public void setIdSquad(String idSquad) {
        this.idSquad = idSquad;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SquadEvent that = (SquadEvent) o;
        return Objects.equals(idSquad, that.idSquad) &&
                Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(idAddress, that.idAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSquad, name, date, description, tag, idAddress);
    }
}

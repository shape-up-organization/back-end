package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class SquadEvent extends Entity<SquadEventId> {
    private String name;
    private LocalDateTime date;
    private String description;
    private String tag;


    public SquadEvent(String name, LocalDateTime date, String description, String tag) {
        super(SquadEventId.unique());
        this.name = name;
        this.date = date;
        this.description = description;
        this.tag = tag;

    }

    public static SquadEvent newSquadEvent(String name,LocalDateTime date,String description,String tag){
        return newSquadEvent(name,date,description,tag);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SquadEvent that = (SquadEvent) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(description, that.description)) return false;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}

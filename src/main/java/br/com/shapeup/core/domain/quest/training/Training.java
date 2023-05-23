package br.com.shapeup.core.domain.quest.training;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.common.domain.enums.ClassificationEnum;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.List;

public class Training extends Entity<TrainingId> {

    private java.lang.String name;
    private CategoryEnum category;
    private Integer duration;
    private java.lang.String description;
    private Long xp;
    private String classification;
    private Long unlockXp;
    List<java.lang.String> exercises;

    Training(
            TrainingId id,
            java.lang.String name,
            CategoryEnum category,
            Integer duration,
            java.lang.String description,
            Long xp,
            String classification,
            Long unlockXp,
            List<java.lang.String> exercises
    ) {
        super(id);
        this.name = name;
        this.category = category;
        this.duration = duration;
        this.description = description;
        this.xp = xp;
        this.classification = classification;
        this.unlockXp = unlockXp;
        this.exercises = exercises;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new TrainingValidator(handler, this).validate();
    }

    public static Training create(
            TrainingId id,
            java.lang.String name,
            CategoryEnum category,
            Integer duration,
            java.lang.String description,
            Long xp,
            String classification,
            Long unlockXp,
            List<java.lang.String> exercises
    ) {
        return new Training(id, name, category, duration, description, xp, classification, unlockXp, exercises);
    }

    public java.lang.String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public Long getXp() {
        return xp;
    }

    public String getClassification() {
        return classification;
    }

    public List<java.lang.String> getExercises() {
        return exercises;
    }

    public Integer getDuration() {
        return duration;
    }

    public Long getUnlockXp() {
        return unlockXp;
    }
}

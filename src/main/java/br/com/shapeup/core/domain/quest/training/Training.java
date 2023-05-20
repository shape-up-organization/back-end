package br.com.shapeup.core.domain.quest.training;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.common.domain.enums.ClassificationEnum;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class Training extends Entity<TrainingId> {

    private String name;
    private CategoryEnum category;
    private Integer duration;
    private String description;
    private Long xp;
    private ClassificationEnum classification;
    private Long unlockXp;
    String exercises;

    Training(
            TrainingId id,
            String name,
            CategoryEnum category,
            Integer duration,
            String description,
            Long xp,
            ClassificationEnum classification,
            Long unlockXp,
            String exercises
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
            String name,
            CategoryEnum category,
            Integer duration,
            String description,
            Long xp,
            ClassificationEnum classification,
            Long unlockXp,
            String exercises
    ) {
        return new Training(id, name, category, duration, description, xp, classification, unlockXp, exercises);
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Long getXp() {
        return xp;
    }

    public ClassificationEnum getClassification() {
        return classification;
    }

    public String getExercises() {
        return exercises;
    }

    public Integer getDuration() {
        return duration;
    }

    public Long getUnlockXp() {
        return unlockXp;
    }
}

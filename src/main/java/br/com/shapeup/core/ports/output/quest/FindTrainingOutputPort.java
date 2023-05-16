package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.core.domain.quest.training.Training;

public interface FindTrainingOutputPort {
    Training findById(String id);
}

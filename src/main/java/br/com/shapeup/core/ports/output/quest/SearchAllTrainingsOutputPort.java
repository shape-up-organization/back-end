package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface SearchAllTrainingsOutputPort {
    List<Training> execute();
}

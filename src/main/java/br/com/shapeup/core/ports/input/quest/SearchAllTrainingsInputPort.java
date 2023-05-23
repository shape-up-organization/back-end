package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface SearchAllTrainingsInputPort {
    List<Training> execute();
}

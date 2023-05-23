package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.input.quest.SearchAllTrainingsInputPort;
import br.com.shapeup.core.ports.output.quest.SearchAllTrainingsOutputPort;
import java.util.List;

public class SearchAllTrainingsUsecase implements SearchAllTrainingsInputPort {

    private final SearchAllTrainingsOutputPort searchAllTrainingsOutputPort;

    public SearchAllTrainingsUsecase(SearchAllTrainingsOutputPort searchAllTrainingsOutputPort) {
        this.searchAllTrainingsOutputPort = searchAllTrainingsOutputPort;
    }

    @Override
    public List<Training> execute() {
        return searchAllTrainingsOutputPort.execute();
    }
}

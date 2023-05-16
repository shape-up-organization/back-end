package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.quest.FindTrainingAdapter;
import br.com.shapeup.adapters.output.integration.quest.FindTrainingDayAdapter;
import br.com.shapeup.adapters.output.integration.quest.InsertTrainingToSpecificDayAdapter;
import br.com.shapeup.adapters.output.integration.quest.QuestAdapter;
import br.com.shapeup.adapters.output.integration.quest.RemoveTrainingDayByTrainingIdAndUserIdAdapter;
import br.com.shapeup.adapters.output.integration.quest.RemoveTrainingFromAUserAdapter;
import br.com.shapeup.adapters.output.integration.quest.UpdateStatusTrainingDayAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.integration.user.UserPersistenceAdapter;
import br.com.shapeup.adapters.output.integration.xp.XpAdapter;
import br.com.shapeup.core.usecase.quest.FinishTrainingUsecase;
import br.com.shapeup.core.usecase.quest.PeriodicUpdateUncompletedUserTrainingUsecase;
import br.com.shapeup.core.usecase.quest.QuestUsecase;
import br.com.shapeup.core.usecase.quest.RemoveTrainingFromUserUsecase;
import br.com.shapeup.core.usecase.quest.TrainingsUserWithFullStatusUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestConfig {

    @Bean
    public QuestUsecase questUsecase(
            QuestAdapter questAdapter,
            FindUserAdapter findUserAdapter,
            InsertTrainingToSpecificDayAdapter insertTrainingToSpecificDayAdapter,
            FindTrainingAdapter findTrainingAdapter,
            UserPersistenceAdapter userPersistenceAdapter
    ) {
        return new QuestUsecase(
                questAdapter,
                findUserAdapter,
                insertTrainingToSpecificDayAdapter,
                findTrainingAdapter,
                userPersistenceAdapter
        );
    }

    @Bean
    public TrainingsUserWithFullStatusUsecase trainingsUserWithFullStatusUsecase(FindTrainingDayAdapter findTrainingDayAdapter) {
        return new TrainingsUserWithFullStatusUsecase(findTrainingDayAdapter);
    }

    @Bean
    public RemoveTrainingFromUserUsecase removeTrainingFromUserUsecase(
            FindTrainingAdapter findTrainingAdapter,
            FindUserAdapter findUserAdapter,
            UserPersistenceAdapter userPersistenceAdapter,
            RemoveTrainingDayByTrainingIdAndUserIdAdapter removeTrainingDayByTrainingIdAndUserIdAdapter,
            RemoveTrainingFromAUserAdapter removeTrainingFromAUserAdapter
    ) {
        return new RemoveTrainingFromUserUsecase(
                findTrainingAdapter,
                findUserAdapter,
                userPersistenceAdapter,
                removeTrainingDayByTrainingIdAndUserIdAdapter,
                removeTrainingFromAUserAdapter
        );
    }

    @Bean
    public FinishTrainingUsecase finishTrainingUsecase(
            FindTrainingAdapter findTrainingAdapter,
            FindUserAdapter findUserAdapter,
            UpdateStatusTrainingDayAdapter updateStatusTrainingDayAdapter,
            XpAdapter xpAdapter,
            FindTrainingDayAdapter findTrainingDayAdapter
    ) {
        return new FinishTrainingUsecase(
                findTrainingAdapter,
                findUserAdapter,
                updateStatusTrainingDayAdapter,
                xpAdapter,
                findTrainingDayAdapter
        );
    }

    @Bean
    public PeriodicUpdateUncompletedUserTrainingUsecase periodicUpdateUncompletedUserTrainingUsecase(
            FindTrainingDayAdapter findTrainingDayAdapter,
            UpdateStatusTrainingDayAdapter updateStatusTrainingDayAdapter
    ) {
        return new PeriodicUpdateUncompletedUserTrainingUsecase(
                findTrainingDayAdapter,
                updateStatusTrainingDayAdapter
        );
    }
}

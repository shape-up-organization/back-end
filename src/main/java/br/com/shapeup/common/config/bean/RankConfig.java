package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.core.ports.output.rank.RankOutput;
import br.com.shapeup.core.usecase.rank.RankUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankConfig {
    @Bean
    public RankUsecase rankUsecase(FindUserAdapter findUserAdapter, RankOutput rankOutput) {
        return new RankUsecase(findUserAdapter, rankOutput);
    }
}

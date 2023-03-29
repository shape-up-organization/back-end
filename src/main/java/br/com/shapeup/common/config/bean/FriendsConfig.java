package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.integration.user.FriendsAdapter;
import br.com.shapeup.adapters.output.integration.user.UserPersistenceAdapter;
import br.com.shapeup.core.usecase.user.FriendsUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendsConfig {

    @Bean
    public FriendsUsecase friendsUsecase(FriendsAdapter friendsAdapter, FindUserAdapter findUserAdapter) {
        return new FriendsUsecase(friendsAdapter, findUserAdapter);
    }

}

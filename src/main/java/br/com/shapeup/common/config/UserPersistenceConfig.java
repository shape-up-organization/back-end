package br.com.shapeup.common.config;

import br.com.shapeup.adapters.output.user.UserPersistenceAdapter;
import br.com.shapeup.core.usecase.UserPersistanceUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserPersistenceConfig {

    @Bean
    public UserPersistanceUsecase userPersistanceUsecase(
            UserPersistenceAdapter userPersistenceAdapter
    ) {

        return new UserPersistanceUsecase(userPersistenceAdapter);
    }
}

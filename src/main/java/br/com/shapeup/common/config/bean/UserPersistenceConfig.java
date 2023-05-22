package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.user.UserPersistenceAdapter;
import br.com.shapeup.core.usecase.user.UserPersistanceUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserPersistenceConfig {

    @Bean
    public UserPersistanceUsecase userPersistanceUsecase(UserPersistenceAdapter userPersistenceAdapter) {

        return new UserPersistanceUsecase(userPersistenceAdapter);
    }
}

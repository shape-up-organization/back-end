package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.user.profile.ProfilePictureAdapter;
import br.com.shapeup.core.usecase.user.profile.ProfilePictureUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfilePictureConfig {

    @Bean
    public ProfilePictureUsecase profilePictureUsecase(ProfilePictureAdapter profilePictureAdapter) {

        return new ProfilePictureUsecase(profilePictureAdapter);
    }
}

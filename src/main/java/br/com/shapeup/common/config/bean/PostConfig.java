package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.post.PostAdapter;
import br.com.shapeup.core.usecase.post.PostUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostConfig {
    @Bean
    public PostUsecase postUsecase(PostAdapter postAdapter) {
        return new PostUsecase(postAdapter);
    }
}

package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.post.PostAdapter;
import br.com.shapeup.adapters.output.integration.post.comment.CommentAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.core.usecase.post.comment.CommentUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentConfig {
    @Bean
    public CommentUsecase commentUsecase(FindUserAdapter findUserAdapter, CommentAdapter commentAdapter, PostAdapter postAdapter) {
        return new CommentUsecase(findUserAdapter, commentAdapter, postAdapter);
    }
}

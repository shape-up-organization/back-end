package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.post.CreatePostAdapter;
import br.com.shapeup.adapters.output.integration.post.PostAdapter;
import br.com.shapeup.adapters.output.integration.post.like.PostLikeAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.core.usecase.post.PostUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostConfig {
    @Bean
    public PostUsecase postUsecase(PostAdapter postAdapter,
                                   PostLikeAdapter postLikeAdapter,
                                   FindUserAdapter findUserAdapter,
                                   CreatePostAdapter createPostAdapter
    ) {
        return new PostUsecase(postAdapter, postLikeAdapter, findUserAdapter, createPostAdapter);
    }
}

package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.friend.FriendshipAdapter;
import br.com.shapeup.adapters.output.integration.post.PostAdapter;
import br.com.shapeup.adapters.output.integration.post.PostS3Adapter;
import br.com.shapeup.adapters.output.integration.post.comment.CommentAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.integration.user.UserPersistenceAdapter;
import br.com.shapeup.core.usecase.user.UserPersistanceUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserPersistenceConfig {

    @Bean
    public UserPersistanceUsecase userPersistanceUsecase(UserPersistenceAdapter userPersistenceAdapter,
                                                         CommentAdapter commentAdapter,
                                                         PostAdapter postAdapter,
                                                         FriendshipAdapter friendshipAdapter,
                                                         FindUserAdapter findUserAdapter,
                                                         PostS3Adapter postS3Adapter) {

        return new UserPersistanceUsecase(userPersistenceAdapter,
                commentAdapter,
                postAdapter,
                friendshipAdapter,
                findUserAdapter,
                postS3Adapter);
    }
}

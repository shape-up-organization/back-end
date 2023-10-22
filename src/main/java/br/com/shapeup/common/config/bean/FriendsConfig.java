package br.com.shapeup.common.config.bean;

import br.com.shapeup.adapters.output.integration.friend.FindFriendshipAdapter;
import br.com.shapeup.adapters.output.integration.friend.FriendshipAdapter;
import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.core.ports.output.friend.FriendshipRequestPublisherOutputPort;
import br.com.shapeup.core.usecase.friend.FriendshipUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendsConfig {

    @Bean
    public FriendshipUsecase friendsUsecase(
            FriendshipAdapter friendsAdapter,
            FindUserAdapter findUserAdapter,
            FindFriendshipAdapter findFriendshipAdapter,
            FriendshipRequestPublisherOutputPort friendshipRequestPublisherOutputPOrt
    ) {
        return new FriendshipUsecase(
                friendsAdapter,
                findUserAdapter,
                findFriendshipAdapter,
                friendshipRequestPublisherOutputPOrt
        );
    }
}

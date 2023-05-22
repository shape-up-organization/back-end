package br.com.shapeup.adapters.output.integration.user.presence;

import br.com.shapeup.adapters.output.repository.model.user.UserPresenceEvent;
import reactor.core.publisher.Flux;

public interface UserPresenceManagerGateway {
    void markUserAsOnline(String userId);
    void markUserAsOffline(String userId);
    boolean isUserOnline(String userId);
    Flux<UserPresenceEvent> getUserPresenceStream(String userId);

}

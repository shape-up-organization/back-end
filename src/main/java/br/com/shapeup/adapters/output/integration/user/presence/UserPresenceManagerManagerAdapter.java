package br.com.shapeup.adapters.output.integration.user.presence;

import br.com.shapeup.adapters.output.repository.model.user.UserPresenceEvent;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class UserPresenceManagerManagerAdapter implements UserPresenceManagerGateway {

    private final CacheManager cacheManager;

    public UserPresenceManagerManagerAdapter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void markUserAsOnline(String userId) {
        cacheManager.getCache("user_presence").put(userId, "online");
    }

    public void markUserAsOffline(String userId) {
        cacheManager.getCache("user_presence").evict(userId);
    }

    public Flux<UserPresenceEvent> getUserPresenceStream(String userId) {

        return Flux.create(sink -> {
            Flux.interval(Duration.ofSeconds(10))
                    .map(it -> new UserPresenceEvent(userId, isUserOnline(userId)))
                    .doOnCancel(() -> markUserAsOffline(userId))
                    .subscribe(sink::next);
        });
    }

    public boolean isUserOnline(String userId) {
        return cacheManager.getCache("user_presence").get(userId) != null;
    }
}

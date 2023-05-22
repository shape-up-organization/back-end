package br.com.shapeup.adapters.output.repository.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class UserPresenceEvent {

    private final String userId;

    private final boolean isOnline;

    public String getUserId() {
        return userId;
    }

    public boolean isOnline() {
        return isOnline;
    }
}

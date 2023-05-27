package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.output.integration.user.presence.UserPresenceManagerManagerAdapter;
import br.com.shapeup.adapters.output.repository.model.user.UserPresenceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/shapeup/presence")
public class UserPresenceController {

    private final UserPresenceManagerManagerAdapter userPresenceManagerAdapter;

    @PostMapping("/mark-online/{userId}")
    public void markUserAsOnline(@PathVariable String userId) {
        userPresenceManagerAdapter.markUserAsOnline(userId);
    }

    @PostMapping("/mark-offline/{userId}")
    public void markUserAsOffline(@PathVariable String userId) {
        userPresenceManagerAdapter.markUserAsOffline(userId);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserPresenceEvent> getUserPresenceStream(@RequestParam String userId) {
        return userPresenceManagerAdapter.getUserPresenceStream(userId);
    }
}

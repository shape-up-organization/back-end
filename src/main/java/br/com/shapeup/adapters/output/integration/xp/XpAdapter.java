package br.com.shapeup.adapters.output.integration.xp;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.core.ports.output.xp.XpOutputPort;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class XpAdapter implements XpOutputPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void addXp(String username, Long xp) {

        Try.run(() -> {
            log.info("Adding {} xp to user {}", xp, username);
            userJpaRepository.findByUsername(username).ifPresent(userEntity -> {
                userEntity.setXp(userEntity.getXp() + Long.valueOf(xp));
                userEntity.setId(userEntity.getId());
                userJpaRepository.save(userEntity);
            });
        }).onSuccess(success -> {
            log.info("XP added successfully");
        }).onFailure(failure -> {
            log.error("Error adding xp to user {}", username, failure);
        });

    }
}

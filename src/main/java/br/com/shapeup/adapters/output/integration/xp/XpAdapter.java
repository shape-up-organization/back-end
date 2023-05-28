package br.com.shapeup.adapters.output.integration.xp;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.User;
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
    private final UserMapper userMapper;

    @Override
    public void addXp(User user, Long xp) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        Try.run(() -> {
            log.info("Adding {} xp to user {}", xp, userEntity.getUsername());
            userEntity.setXp(userEntity.getXp() + xp);
            userEntity.setId(userEntity.getId());
            userJpaRepository.save(userEntity);
        }).onSuccess(success -> {
            log.info("XP added successfully");
        }).onFailure(failure -> {
            log.error("Error adding xp to user {}", userEntity.getUsername(), failure);
        });

    }
}

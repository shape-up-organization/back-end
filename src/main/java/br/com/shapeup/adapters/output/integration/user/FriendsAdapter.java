package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.core.ports.output.user.FriendsOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendsAdapter implements FriendsOutput {

    private final UserRepositoryJpa userRepositoryJpa;

    private final UserMapper userMapper;

}

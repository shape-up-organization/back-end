package br.com.shapeup.core.ports.output.user;

import br.com.shapeup.core.domain.user.User;
import java.util.UUID;

public interface FindUserOutput {
    User findByEmail(String email);
    User findByUsername(String username);
    User findById(UUID id);
}

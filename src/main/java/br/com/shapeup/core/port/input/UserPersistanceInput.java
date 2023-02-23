package br.com.shapeup.core.port.input;

import br.com.shapeup.core.domain.User;

public interface UserPersistanceInput {
    public void save(User user);
}

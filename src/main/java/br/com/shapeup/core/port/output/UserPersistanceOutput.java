package br.com.shapeup.core.port.output;

import br.com.shapeup.core.domain.User;

public interface UserPersistanceOutput {
    public void save(User user);
}

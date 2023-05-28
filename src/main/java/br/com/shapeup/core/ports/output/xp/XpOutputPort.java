package br.com.shapeup.core.ports.output.xp;

import br.com.shapeup.core.domain.user.User;

public interface XpOutputPort {
    void addXp(User user, Long xp);
}

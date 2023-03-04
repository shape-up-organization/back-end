package br.com.shapeup.factories;

import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import java.time.Instant;
import java.util.Date;

public class UserFactory {
    private static UserFactory instace = null;

    public static UserFactory getInstance() {
        if (instace == null) {
            instace = new UserFactory();
        }
        return instace;
    }

    public User create() {
        return User.newUser("Iara", "Alves", Email.create("iara@gmail.com"), CellPhone.create("11983323932"),
                Password.create("iara123"), Birth.create(Date.from(Instant.now())));
    }
}

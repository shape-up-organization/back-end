package br.com.shapeup.factories;

import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.user.UserId;

public class UserFactory {
    private static UserFactory instace = null;

    public static UserFactory getInstance() {
        if (instace == null) {
            instace = new UserFactory();
        }
        return instace;
    }

    public User create() {
        return new User(UserId.unique(), "Iara", "Alves", Email.create("iara@gmail.com"),
                CellPhone.create("11983323932"), Password.create("iara123"));
    }
}

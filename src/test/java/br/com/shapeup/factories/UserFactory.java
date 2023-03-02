package br.com.shapeup.factories;

import br.com.shapeup.core.domain.User;

public class UserFactory {
    private static UserFactory instace = null;

    public static UserFactory getInstance() {
        if (instace == null) {
            instace = new UserFactory();
        }
        return instace;
    }

    public User create() {
        return new User("Iara", "Alves", "iara.alves@email.com", "11983324323", "iara123");
    }
}

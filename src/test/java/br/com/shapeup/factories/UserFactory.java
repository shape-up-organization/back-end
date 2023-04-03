package br.com.shapeup.factories;

import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UserFactory {
    private static UserFactory instace = null;

    public static UserFactory getInstance() {
        if (instace == null) {
            instace = new UserFactory();
        }
        return instace;
    }

    public User create() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse("05/10/2000", formatter);
        User user = User.newUser("Iara", "Alves", "iarinha", Email.create("iara@gmail.com"), CellPhone.create("11983323932"),
                Password.create("iara123"), Birth.create(localDate), UUID.randomUUID());
        return user;
    }
}

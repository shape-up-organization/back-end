package br.com.shapeup.core.domain.user;

import br.com.shapeup.core.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class User extends Entity<UserId> {
    private String name;
    private String lastName;
    private Email email;
    private CellPhone cellPhone;
    private Password password;

    public User(UserId id, String name, String lastName, Email email, CellPhone cellPhone, Password password) {
        super(id);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.cellPhone = cellPhone;
        this.password = password;
    }

    public static User newUser(String name, String lastName, Email email, CellPhone cellPhone, Password password) {
        var id = UserId.unique();
        return new User(id, name, lastName, email, cellPhone, password);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public CellPhone getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(CellPhone cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (!name.equals(user.name))
            return false;
        if (!lastName.equals(user.lastName))
            return false;
        if (!email.equals(user.email))
            return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}

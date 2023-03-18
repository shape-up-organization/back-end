package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class User extends Entity<UserId> {
    private String name;
    private String lastName;
    private String username;
    private Email email;
    private CellPhone cellPhone;
    private Password password;
    private Birth birth;
    private String biography;

    public User(String name, String lastName, String username, Email email, CellPhone cellPhone, Password password,
            Birth birth) {
        super(UserId.unique());
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.cellPhone = cellPhone;
        this.password = password;
        this.birth = birth;
        this.biography = "";
    }

    public static User newUser(String name, String lastName, String username, Email email, CellPhone cellPhone,
            Password password, Birth birth) {
        var id = UserId.unique();
        return new User(name, lastName, username, email, cellPhone, password, birth);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }

    public void validateValueObjects() {
        validate(new ThrowsValidationHandler());
        email.validateEmail();
        cellPhone.validateCellPhone();
        password.validatePassword();
        birth.validateBirth();
    }

    public void validateName() {
        new UserValidator(new ThrowsValidationHandler(), this).validateName();
    }

    public void validateLastName() {
        new UserValidator(new ThrowsValidationHandler(), this).validateLastName();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        User user = (User) o;
        return getName().equals(user.getName()) && getLastName().equals(user.getLastName())
                && getUsername().equals(user.getUsername()) && getEmail().equals(user.getEmail())
                && getCellPhone().equals(user.getCellPhone()) && getPassword().equals(user.getPassword())
                && getBirth().equals(user.getBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getLastName(), getUsername(), getEmail(), getCellPhone(),
                getPassword(), getBirth());
    }
}

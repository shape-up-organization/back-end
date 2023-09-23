package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidLastName;
import br.com.shapeup.common.exceptions.user.UserInvalidNameException;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;

public class FullName extends ValueObject {
    private String firstName;
    private String lastName;

    private FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static FullName create(String name, String lastName) {
        return new FullName(name, lastName);
    }

    public void validateLastName() {
        checkLastNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.getFirstName();
        if (name == null) {
            throw new UserInvalidNameException("name should not be null");
        }

        final int lenght = name.trim().length();
        if (lenght > 255 || lenght < 2) {
            throw new UserInvalidNameException("name must be between 2 and 255 characters");
        }

        if(name.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new UserInvalidPasswordException("name shouldn't contain special character");
        }
    }

    private void checkLastNameConstraints() {
        final var lastName = this.getLastName();
        if (lastName == null) {
            throw new UserInvalidLastName("lastName should not be null");
        }

        if (lastName.isBlank()) {
            throw new UserInvalidLastName("lastName should not be empty");
        }

        final int lenght = lastName.trim().length();
        if (lenght > 255 || lenght < 2) {
            throw new UserInvalidLastName("lastName must be between 2 and 255 characters");
        }

        if(lastName.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new UserInvalidPasswordException("lastName shouldn't contain special character");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String get() {
        return String.format("%s %s", firstName, lastName);
    }
}

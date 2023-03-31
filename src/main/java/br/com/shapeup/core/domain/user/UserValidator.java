package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.exceptions.user.UserInvalidLastName;
import br.com.shapeup.common.exceptions.user.UserInvalidNameException;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class UserValidator extends Validator {
    private final User user;

    protected UserValidator(ValidationHandler anHandler, User user) {
        super(anHandler);
        this.user = user;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkLastNameConstraints();
    }
    public void validateName() {
        checkNameConstraints();
    }

    public void validateLastName() {
        checkLastNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.user.getName();
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
        final var lastName = this.user.getLastName();
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
}

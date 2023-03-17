package br.com.shapeup.core.domain.user;

import br.com.shapeup.core.domain.validation.Error;
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
    }

    private void checkNameConstraints() {
        final var name = this.user.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int lenght = name.trim().length();
        if (lenght > 255 || lenght < 2) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
            return;
        }
    }

    private void checkLastName() {
        final var lastName = this.user.getLastName();
        if (lastName == null) {
            this.validationHandler().append(new Error("'lastName' should not be null"));
            return;
        }

        if (lastName.isBlank()) {
            this.validationHandler().append(new Error("'lastName' should not be empty"));
            return;
        }

        final int lenght = lastName.trim().length();
        if (lenght > 255 || lenght < 2) {
            this.validationHandler().append(new Error("'lastName' must be between 3 and 255 characters"));
            return;
        }
    }

    private void checkUsername() {
        final var username = this.user.getUsername();
        if (username == null) {
            this.validationHandler().append(new Error("'username' should not be null"));
            return;
        }

        if (username.isBlank()) {
            this.validationHandler().append(new Error("'username' should not be empty"));
            return;
        }

        final int lenght = username.trim().length();
        if (lenght > 255 || lenght < 2) {
            this.validationHandler().append(new Error("'username' must be between 3 and 255 characters"));
            return;
        }
    }
}

package br.com.shapeup.core.domain.validation;

public class Validator {
    private final ValidationHandler handler;
    protected Validator(final ValidationHandler anHandler) {
        this.handler = anHandler;
    }

    public abstract void validate();
    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}

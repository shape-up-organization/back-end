package br.com.shapeup.core.domain.exceptions;

import br.com.shapeup.core.domain.validation.Error;
import java.util.List;

public class DomainException extends NoStackTraceException {
    private final List<Error> errors;

    public DomainException(final String aMessage, List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }



    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}

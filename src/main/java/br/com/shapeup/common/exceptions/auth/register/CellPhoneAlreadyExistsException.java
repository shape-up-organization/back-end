package br.com.shapeup.common.exceptions.auth.register;

import org.springframework.dao.DataIntegrityViolationException;

public class CellPhoneAlreadyExistsException extends DataIntegrityViolationException {
    public CellPhoneAlreadyExistsException() {
        super("Cell phone is not available");
    }
}

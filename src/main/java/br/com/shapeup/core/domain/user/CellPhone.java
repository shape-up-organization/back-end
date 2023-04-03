package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidCellPhoneException;

public class CellPhone extends ValueObject {
    private String value;

    private CellPhone(String value) {
        this.value = value;
    }

    public static CellPhone create(String cellPhone) {
        return new CellPhone(cellPhone);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void validateCellPhone(String cellPhone){
        if(cellPhone.matches(".*[a-zA-Z].*")){
            throw new UserInvalidCellPhoneException("cellphone should contain only numbers");
        }

        if(cellPhone.length() > 11){
            throw new UserInvalidCellPhoneException("cellphone should contain less than 11 characters");
        }
        if(cellPhone.length() < 9){
            throw new UserInvalidCellPhoneException("cellphone should contain more than 9 characters");
        }
    }
}

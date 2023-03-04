package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;

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
}

package br.com.shapeup.core.domain.valueobjects.user;

public class CellPhone {
    private String value;

    public CellPhone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

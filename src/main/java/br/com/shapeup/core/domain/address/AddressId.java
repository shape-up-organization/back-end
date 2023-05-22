package br.com.shapeup.core.domain.address;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class AddressId  extends Identifier<String> {
    private final String value;

    private AddressId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static AddressId unique() {
        return AddressId.from(UUID.randomUUID());
    }

    public static AddressId from(final String anId) {
        return new AddressId(anId);
    }

    public static AddressId from(final UUID anId) {
        return new AddressId(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AddressId addressId = (AddressId) o;
        return getValue().equals(addressId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

package br.com.shapeup.common.domain;

import java.util.Objects;

public abstract class Identifier<T> extends ValueObject{
    private final T value;

    protected Identifier(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Identifier<?> that = (Identifier<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public T getValue() {
        return value;
    }
}

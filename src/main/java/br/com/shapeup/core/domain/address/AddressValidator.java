package br.com.shapeup.core.domain.address;

import br.com.shapeup.common.exceptions.address.InvalidAddressException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class AddressValidator extends Validator {
    private final Address address;

    protected AddressValidator(ValidationHandler anHandler, Address address) {
        super(anHandler);
        this.address = address;
    }

    @Override
    public void validate() {
        validateStreet();
        validateNumber();
        validateNeighborhood();
        validateCity();
        validateState();
        validateCountry();
    }
    private void validateStreet() {
        String street = this.address.getStreet();
        if (street == null || street.isBlank()) {
            throw new InvalidAddressException("'Street' should not be null");
        }

        if (street.length() < 3) {
            throw new InvalidAddressException("'Street' should have at least 3 characters");
        }

        if (street.length() > 255) {
            throw new InvalidAddressException("'Street' should have at most 255 characters");
        }
    }

    private void validateNumber() {
        String number = this.address.getNumber();
        if (number == null || number.isBlank()) {
            throw new InvalidAddressException("'Number' should not be null");
        }

        if (number.length() < 1) {
            throw new InvalidAddressException("'Number' should have at least 1 character");
        }

        if (number.length() > 255) {
            throw new InvalidAddressException("'Number' should have at most 255 characters");
        }
    }

    private void validateNeighborhood() {
        String neighborhood = this.address.getNeighborhood();
        if (neighborhood == null || neighborhood.isBlank()) {
            throw new InvalidAddressException("'Neighborhood' should not be null");
        }

        if (neighborhood.length() < 3) {
            throw new InvalidAddressException("'Neighborhood' should have at least 3 characters");
        }

        if (neighborhood.length() > 255) {
            throw new InvalidAddressException("'Neighborhood' should have at most 255 characters");
        }
    }

    private void validateCity() {
        String city = this.address.getCity();
        if (city == null || city.isBlank()) {
            throw new InvalidAddressException("'City' should not be null");
        }

        if (city.length() < 3) {
            throw new InvalidAddressException("'City' should have at least 3 characters");
        }

        if (city.length() > 255) {
            throw new InvalidAddressException("'City' should have at most 255 characters");
        }
    }

    private void validateState() {
        String state = this.address.getState();
        if (state == null || state.isBlank()) {
            throw new InvalidAddressException("'State' should not be null");
        }

        if (state.length() < 3) {
            throw new InvalidAddressException("'State' should have at least 3 characters");
        }

        if (state.length() > 255) {
            throw new InvalidAddressException("'State' should have at most 255 characters");
        }
    }

    private void validateCountry() {
        String country = this.address.getCounty();
        if (country == null || country.isBlank()) {
            throw new InvalidAddressException("'Country' should not be null");
        }

        if (country.length() < 3) {
            throw new InvalidAddressException("'Country' should have at least 3 characters");
        }

        if (country.length() > 255) {
            throw new InvalidAddressException("'Country' should have at most 255 characters");
        }
    }
}

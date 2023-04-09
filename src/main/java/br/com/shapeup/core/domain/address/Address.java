package br.com.shapeup.core.domain.address;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class Address extends Entity<AddressId> {
    private String street;

    private String number;

    private String neighborhood;

    private String city;

    private String state;

    private String complement;

    private String county;

    private Address(AddressId id, String street, String number
            , String neighborhood, String city, String state
            , String complement, String county) {
        super(id);
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.complement = complement;
        this.county = county;
    }

    public static Address newAddress(String street,String number,String neighborhood,String city
    ,String state,String complement, String county, AddressId id)
    {
        return new Address(id, street, number, neighborhood, city, state, complement, county);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new AddressValidator(handler, this).validate();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        Address address = (Address) o;

        return getCity().equals(address.getCity()) &&
                getCounty().equals(address.getCounty()) &&
                getComplement().equals(address.getComplement()) &&
                getState().equals(address.getState()) &&
                getNumber().equals(address.getNumber()) &&
                getNeighborhood().equals(address.getNeighborhood()) &&
                getStreet().equals(address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCity(), getCounty(), getComplement(), getState(), getNumber(),
                getNeighborhood(), getNumber(), getStreet());
    }
}

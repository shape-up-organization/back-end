package br.com.shapeup.core.domain.address;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import jakarta.persistence.Column;

import java.util.Objects;

public class Address extends Entity<AddressId> {

    private String street;


    private String number;


    private String neighborhood;


    private String city;


    private String state;


    private String complement;


    private String county;

    public Address(String street, String number
            , String neighborhood, String city, String state
            , String complement, String county) {
        super(AddressId.unique());
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.complement = complement;
        this.county = county;
    }
    public static Address newAddress(String street,String number,String neighborhood,String city
    ,String state,String complement, String county){
        return newAddress(street, number, neighborhood, city, state, complement, county);
    }

    @Override
    public void validate(ValidationHandler handler) {

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Address address = (Address) o;

        if (!Objects.equals(street, address.street)) return false;
        if (!Objects.equals(number, address.number)) return false;
        if (!Objects.equals(neighborhood, address.neighborhood))
            return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(state, address.state)) return false;
        if (!Objects.equals(complement, address.complement)) return false;
        return Objects.equals(county, address.county);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (complement != null ? complement.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        return result;
    }
}

package br.com.shapeup.core.domain.valueobjects;

import java.time.Instant;
import java.time.ZonedDateTime;

public class Address {
    private String state;
    private String city;
    private String street;
    private String uf;
    private String neighborhood;
    private String number;
    private String complement;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Instant deletedAt;

    public Address(String state, String city, String street, String uf, String neighborhood, String number, String complement) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.uf = uf;
        this.neighborhood = neighborhood;
        this.number = number;
        this.complement = complement;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}

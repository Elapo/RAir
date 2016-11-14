package com.realdolmen.rair.domain.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {

    @NotNull
    @Size(min = 1, message = "Street must not be empty")
    private String street;

    @NotNull
    @Size(min = 1, message = "Number must not be empty")
    private String number;

    @NotNull
    @Size(min = 1, message = "City must not be empty")
    private String city;

    @NotNull
    @Size(min = 1, message = "Postal code must not be empty")
    private String postalCode;

    @NotNull
    @Size(min = 1, message = "Country must not be empty")
    private String country;

    public Address() {

    }


    public Address(String street, String number, String city, String postalCode, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (number != null ? !number.equals(address.number) : address.number != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (postalCode != null ? !postalCode.equals(address.postalCode) : address.postalCode != null) return false;
        return country != null ? country.equals(address.country) : address.country == null;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}

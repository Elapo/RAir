package com.realdolmen.rair;

import com.realdolmen.rair.domain.entities.Address;

import javax.inject.Named;
import java.util.Date;

@Named (value = "register")
public class RegisterBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    private String lastName;
    private String firstName;
    private Date birthDate;
    private Enum selectedSex;
    private String email;
    private String phoneNumber;
    private Address address;
    private Sex sexEnum;

    //endregion	 

    //region Private Properties - 

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors +

    public RegisterBean() {}

    //endregion

    //region Public Properties +

    public Enum getSelectedSex() {
        return selectedSex;
    }

    public void setSelectedSex(Enum selectedSex) {
        this.selectedSex = selectedSex;
    }

    public Sex[] getSexEnum() {
        return Sex.values();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //endregion

    //region Public Methods -

    public void register() {

    }

    //endregion

}

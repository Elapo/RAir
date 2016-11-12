package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.domain.controllers.UserController;
import com.realdolmen.rair.domain.entities.Address;
import com.realdolmen.rair.domain.entities.user.ContactInformation;
import com.realdolmen.rair.domain.entities.user.RegularUser;
import com.realdolmen.rair.domain.entities.user.Sex;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Named (value = "register")
@RequestScoped
public class RegisterBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    @Inject
    private UserController userController;

    private String lastName;
    private String firstName;

    private Date birthDate = new Date();
    private Sex selectedSex;
    private String email;
    private String phoneNumber;
    private Address address;
    private Sex sexEnum;
    private String password;

    //endregion	 

    //region Private Properties -

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors +

    public RegisterBean() {
        this.address = new Address();
    }

    //endregion

    //region Public Properties +

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sex getSelectedSex() {
        return selectedSex;
    }

    public void setSelectedSex(Sex selectedSex) {
        this.selectedSex = selectedSex;
    }

    public Sex[] getSexEnum() {
        return this.sexEnum.values();
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

    //region Public Methods +

    public String register() {
        User user = new RegularUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setPassword(password);
        } catch (NoSuchAlgorithmException algo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(algo.getMessage()));
            return "register";
        }
        user.setContactInformation(new ContactInformation(email, phoneNumber, address));
        userController.register(user);
        return "pretty:login";
    }

    //endregion

}

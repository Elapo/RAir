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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    private Date birthDate;
    private Sex selectedSex;

    @Pattern(message = "- E-mail is not a valid e-mail address.", regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")
    private String email;

    private String phoneNumber;
    private Address address;
    private Sex sexEnum;

    @Size(min = 5, message = "- Password is required and atleast exist out of 5 characters.")
    private String password;

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
            return "pretty:register";
        }
        user.setContactInformation(new ContactInformation(email, phoneNumber, address));
        userController.register(user);
        return "pretty:login";
    }

    //endregion

}

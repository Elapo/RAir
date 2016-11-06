package com.realdolmen.rair.domain.entities.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.security.NoSuchAlgorithmException;

@Entity
@DiscriminatorValue("CU")
public class CompanyUser extends User {

    public CompanyUser() {
    }

    CompanyUser(String firstName, String lastName, String email, String phoneNumber, String password, Address address) throws NoSuchAlgorithmException {
        super(firstName, lastName, email, phoneNumber, password, address);
    }
}

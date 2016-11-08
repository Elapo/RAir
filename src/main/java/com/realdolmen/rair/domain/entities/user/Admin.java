package com.realdolmen.rair.domain.entities.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.security.NoSuchAlgorithmException;

@Entity
@DiscriminatorValue("AD")
public class Admin extends User {
    public Admin() {
    }

    Admin(String firstName, String lastName, String password, ContactInformation contactInformation) throws NoSuchAlgorithmException {
        super(firstName, lastName, password, contactInformation);
    }
}

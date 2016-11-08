package com.realdolmen.rair.domain.entities.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.security.NoSuchAlgorithmException;

@Entity
@DiscriminatorValue("RU")
public class RegularUser extends User {

    RegularUser(String firstName, String lastName, String password, ContactInformation contactInformation) throws NoSuchAlgorithmException {
        super(firstName, lastName, password, contactInformation);
    }

    public RegularUser() {
    }
}

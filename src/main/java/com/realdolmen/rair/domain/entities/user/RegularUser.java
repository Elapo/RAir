package com.realdolmen.rair.domain.entities.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.security.NoSuchAlgorithmException;

@Entity
@DiscriminatorValue("RU")
public class RegularUser extends User {

    protected RegularUser(String firstName, String lastName, String email, String phoneNumber, String password, Address address) throws NoSuchAlgorithmException {
        super(firstName, lastName, email, phoneNumber, password, address);
    }

    public RegularUser() {
    }
}

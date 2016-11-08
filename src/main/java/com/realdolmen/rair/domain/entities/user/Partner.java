package com.realdolmen.rair.domain.entities.user;

import com.realdolmen.rair.domain.entities.Flight;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PA")
public class Partner extends User {

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Flight> ownedFlights = new ArrayList<>();

    public Partner() {
    }

    Partner(String firstName, String lastName, String password, ContactInformation contactInformation) throws NoSuchAlgorithmException {
        super(firstName, lastName, password, contactInformation);
    }

    public List<Flight> getOwnedFlights() {
        return ownedFlights;
    }

    public void setOwnedFlights(List<Flight> ownedFlights) {
        this.ownedFlights = ownedFlights;
    }
}

package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Flight;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class FlightManagementBean implements Serializable {

    @Inject
    private FlightController flightController;

    public List<Flight> getAllFlights() {
        return flightController.getAllFlights();
    }
}

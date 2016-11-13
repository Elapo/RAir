package com.realdolmen.rair.domain.jsf.flight;

import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Flight;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class FlightDetailsBean {

    private long flightId;

    private Flight flight;

    @Inject
    private FlightController controller;

    public void load() {
        flight = controller.getFlight(flightId);
        if(flight == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Flight '" + flightId + "' is not found!"));
        }
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}

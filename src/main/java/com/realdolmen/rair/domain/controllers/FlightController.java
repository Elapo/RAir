package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.entities.Flight;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type;
import java.io.Serializable;
import java.util.List;

@Named
public class FlightController extends AbstractController implements Serializable {

    @Inject
    private FlightDao flightDao;

    public void registerFlight(Flight flight) {
        flightDao.insert(flight);
    }

    public void deactivateFlight(Flight flight) {
        flightDao.deactivate(flight);
    }

    public void activateFlight(Flight flight) {
        flightDao.activate(flight);
    }

    public List<Flight> getActiveFlights() {
        return flightDao.getActiveFlights();
    }

    public List<Flight> getAllFlights() {
        return flightDao.getAllFlights();
    }

    public List<Flight> getInactiveFlights() {
        return flightDao.getInactiveFlights();
    }

    public List<Flight> getFlightsBySearch(Flight f) throws IllegalAccessException {
        return flightDao.getFlightsBySearch(f);
    }
}

package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.entities.Flight;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class FlightController extends AbstractController {

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
}

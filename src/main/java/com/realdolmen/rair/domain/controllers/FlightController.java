package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.Route;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
public class FlightController extends AbstractController {

    @Inject
    private FlightDao flightDao;

    @Inject
    private RouteController routeController;

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

    public void registerFlight(Airport from, Airport to, Flight flight) {
        Route route = routeController.getRouteByAirports(from, to);
        if (route == null) {
            route = routeController.createRoute(from, to);
        }

        flight.setRoute(flightDao.em().find(Route.class, route.getId()));
        registerFlight(flight);

        route.getFlights().add(flight);
        routeController.update(route);
    }

    public void updateFlightStatus(Long flightId) {
        System.out.println(flightId);
//        flightDao.update(flight);
    }

    public void updateFlightStatus(Flight flight) {
        flightDao.update(flight);
    }

    public Flight getFlight(long flightId) {
        return flightDao.find(flightId);
    }
}

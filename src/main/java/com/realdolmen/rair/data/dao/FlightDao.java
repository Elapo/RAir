package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class FlightDao extends AbstractDao<Flight, Long> {

    @Inject
    private BookingDao bookingDao;

    @Inject
    private FlightDao flightDao;

    @Override
    protected Class<Flight> getDataType() {
        return Flight.class;
    }


    @Override
    public void remove(Flight object) {
        bookingDao.getBookingsByFlight(object).forEach(bookingDao::remove);
        object.getRoute().getFlights().remove(object);
        object.setRoute(null);

        object.getCreator().getOwnedFlights().remove(object);
        super.remove(object);
    }

    public void deactivate(Flight flight) {
        flight.deactivate();
        update(flight);
    }

    public void activate(Flight flight) {
        flight.activate();
        update(flight);
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

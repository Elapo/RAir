package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class FlightDao extends AbstractDao<Flight, Long> {

    @Inject
    private BookingDao bookingDao;

    @Override
    protected Class<Flight> getDataType() {
        return Flight.class;
    }


    @Override
    public void remove(Flight object) {
        object.getRoute().getFlights().remove(object);
        object.setRoute(null);

        object.getCreator().getOwnedFlights().remove(object);
        super.remove(object);
    }
}

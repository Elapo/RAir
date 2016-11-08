package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import java.util.List;

public class BookingDao extends AbstractDao<Booking, Long> {

    @Override
    protected Class<Booking> getDataType() {
        return Booking.class;
    }

    @Override
    public void remove(Booking booking) {
        booking.deactivate();
        update(booking);
    }

    public List<Booking> getBookingsByFlight(Flight flight) {
        return em().createNamedQuery("Booking.findByFlight", Booking.class).setParameter("flight", flight).getResultList();
    }
}

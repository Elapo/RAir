package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.BookingStatus;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
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

    public void cancelBooking(Booking booking) {
        booking.setStatus(BookingStatus.CANCELLED);
        update(booking);
    }

    public List<Booking> getBookingsByUser(User u) {
        return em().createNamedQuery("Booking.findByUser",  Booking.class).setParameter("user", u).getResultList();
    }
}

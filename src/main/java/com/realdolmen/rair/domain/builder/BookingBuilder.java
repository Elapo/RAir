package com.realdolmen.rair.domain.builder;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.BookingStatus;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.Ticket;
import com.realdolmen.rair.domain.modifiers.MarginModifier;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingBuilder {
    private List<Ticket> tickets = new ArrayList<>();
    private List<PriceModifier> priceModifiers = new ArrayList<>();

    private Date purchaseTime;
    private BookingStatus status = BookingStatus.PENDING;
    private Flight flight;

    private boolean active = true;

    public BookingBuilder addTicket(Ticket ticket) {
        tickets.add(ticket);
        return this;
    }

    public BookingBuilder addModifier(PriceModifier modifier) {
        priceModifiers.add(modifier);
        return this;
    }

    public BookingBuilder flight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public BookingBuilder purchasedOn(Date date) {
        purchaseTime = date;
        return this;
    }

    public BookingBuilder status(BookingStatus bookingStatus) {
        this.status = bookingStatus;
        return this;
    }

    public BookingBuilder active(boolean flag) {
        this.active = flag;
        return this;
    }

    public Booking build() throws IllegalStateException {
        if (flight == null) {
            throw new IllegalStateException("Flight must be filled in!");
        }
        Booking booking = new Booking();
        booking.setTickets(tickets);
        priceModifiers.add(new MarginModifier());
        booking.setPriceModifiers(priceModifiers);
        booking.setPurchaseTime(purchaseTime);
        booking.setStatus(status);
        if (active)
            booking.activate();
        else
            booking.deactivate();
        booking.setFlight(flight);
        return booking;
    }
}

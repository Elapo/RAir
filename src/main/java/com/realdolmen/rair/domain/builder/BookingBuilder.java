package com.realdolmen.rair.domain.builder;

import com.realdolmen.rair.domain.entities.*;
import com.realdolmen.rair.domain.entities.user.User;
import com.realdolmen.rair.domain.modifiers.ModifierPipeline;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingBuilder {
    private List<Ticket> tickets = new ArrayList<>();
    private List<PriceModifier> priceModifiers = new ArrayList<>();

    private Date purchaseTime;
    private BookingStatus status = BookingStatus.PENDING;
    private Flight flight;

    private PaymentMethod method;

    private User user;

    private FlightClass flightClass;

    private boolean active = true;

    public BookingBuilder addTicket(Ticket ticket) {
        tickets.add(ticket);
        return this;
    }

    public BookingBuilder addTickets(int size) {
        for (int i = 0; i < size; i++) {
            addTicket(new Ticket());
        }

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

    public BookingBuilder user(User user) {
        this.user = user;
        return this;
    }

    public BookingBuilder flightClass(FlightClass fc) {
        flightClass = fc;
        return this;
    }

    public Booking build() throws IllegalStateException {
        if (flight == null) {
            throw new IllegalStateException("Flight must be filled in!");
        }

        if (flightClass == null) {
            throw new IllegalStateException("Flight class must be filled in!");
        }

        if (flight.getBasePrices() == null) {
            throw new IllegalStateException("Base prices must be filled in!");
        }

        if (priceModifiers == null) {
            throw new IllegalStateException("Price modifiers must not be null!");
        }
        Booking booking = new Booking();
        booking.setTickets(tickets);
        booking.setPriceModifiers(priceModifiers);
        booking.setPurchaseTime(purchaseTime);
        booking.setStatus(status);
        if (active)
            booking.activate();
        else
            booking.deactivate();
        booking.setFlight(flight);
        booking.setPaymentMethod(method);

        for (Ticket ticket : tickets) {
            ticket.setBooking(booking);
            if (user != null) {
                ticket.setOwner(user);
            }
        }

        ModifierPipeline pipeline = ModifierPipeline.loadIntoOrder(priceModifiers);

        BigDecimal basePrice = flight.getBasePrices().get(flightClass);


        BigDecimal result = pipeline.pass(basePrice, booking).multiply(new BigDecimal(tickets.size()));
        booking.setFinalPrice(result);
        return booking;
    }

    public BookingBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.method = paymentMethod;
        return this;
    }
}

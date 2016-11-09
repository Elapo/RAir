package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.data.dao.BookingDao;
import com.realdolmen.rair.domain.builder.BookingBuilder;
import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Ticket;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Named
public class BookingController extends AbstractController {

    @Inject
    private BookingDao bookingDao;

    public BookingBuilder newBookingBuilder() {
        return new BookingBuilder();
    }

    public void registerBooking(Booking booking) {
        bookingDao.insert(booking);
    }

    public BigDecimal calculatePrice(Booking booking) {
        BigDecimal total = new BigDecimal(0);
        for(Ticket ticket : booking.getTickets()) {
            BigDecimal basePrice = booking.getFlight().getBasePrices().get(ticket.getFlightClass());
            total = total.add(runThroughModifiers(basePrice, booking.getPriceModifiers()));
        }
        return total;
    }

    BigDecimal runThroughModifiers(BigDecimal input, List<PriceModifier> modifiers) {
        for(PriceModifier mod : modifiers) {
            input = mod.modify(modifiers, input);
        }
        return input;
    }
}

package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.modifiers.CreditCardModifier;
import com.realdolmen.rair.domain.modifiers.MarginModifier;
import com.realdolmen.rair.domain.modifiers.PriceModifier;
import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class BookingControllerTest extends JpaPersistenceTest {

    private BookingController bc;

    private MarginModifier marginModifier;

    @Before
    public void setUp() throws Exception {
        bc = new BookingController();
        marginModifier = new MarginModifier();
        marginModifier.setMargin(100.0);
        marginModifier.setPercentBased(false);
    }

    @Test
    public void newBookingBuilder() throws Exception {

    }

    @Test
    public void registerBooking() throws Exception {

    }

    @Test
    public void calculatePrice() throws Exception {
        Booking booking = entityManager().find(Booking.class, 1000L);
        BigDecimal bd = bc.calculatePrice(booking);
        assertEquals(new BigDecimal(104.5).setScale(2), bd);
    }

    @Test
    public void runThroughModifiers() throws Exception {
        List<PriceModifier> modifiers = Arrays.asList(new CreditCardModifier(), new MarginModifier());
        Booking booking = entityManager().find(Booking.class, 1000L);
        BigDecimal base = new BigDecimal(100);
        BigDecimal result = bc.runThroughModifiers(base, booking, modifiers);

        assertEquals(new BigDecimal(104.50).setScale(2), result);
    }

}
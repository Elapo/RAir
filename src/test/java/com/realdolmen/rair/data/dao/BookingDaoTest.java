package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookingDaoTest extends JpaPersistenceTest {

    private BookingDao bd;

    @Before
    public void setUp() throws Exception {
        bd = new BookingDao();
        bd.setEntityManager(entityManager());
    }

    @Test
    public void testFindByFlight() throws Exception {
        Flight flight = entityManager().find(Flight.class, 1000L);
        assertFalse(bd.getBookingsByFlight(flight).isEmpty());
    }
}
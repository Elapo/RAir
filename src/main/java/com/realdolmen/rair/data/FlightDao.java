package com.realdolmen.rair.data;

import com.realdolmen.rair.domain.entities.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class FlightDao {

    @Inject
    private EntityManager em;

    @Transactional
    public void registerFlight(Flight flight) {
        if(flight.getRoute() == null) {
            throw new IllegalStateException("Flight must be assigned to a route!");
        }
        em.persist(flight);
    }

    public List<Flight> getFlights() {
        return em.createNamedQuery("Flight.findAll", Flight.class).getResultList();
    }

    @Transactional
    public void deleteFlight(Flight flight) {
        if(flight == null) {
            throw new IllegalArgumentException("Flight cannot be null!");
        }
        Flight reference = em.getReference(Flight.class, flight);

        if(reference == null) {
            throw new NoSuchElementException(String.format("Flight #%s cannot be removed because it does not exist!", flight.getId()));
        }
        em.remove(reference);
    }
}

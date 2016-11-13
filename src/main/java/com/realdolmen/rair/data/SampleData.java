package com.realdolmen.rair.data;

import com.realdolmen.rair.domain.entities.*;
import com.realdolmen.rair.domain.entities.user.CompanyUser;
import com.realdolmen.rair.domain.entities.user.ContactInformation;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.entities.user.RegularUser;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Startup
@Singleton
public class SampleData {

    @PersistenceContext
    private EntityManager entityManager;


    @PostConstruct
    public void startup() {
        Partner partner = new Partner();
        partner.setCompanyName("Jetair");
        ContactInformation ci = new ContactInformation("mail@jetair.be", null, null);
        partner.setContactInformation(ci);

        CompanyUser cu = new CompanyUser();
        cu.setFirstName("Company");
        cu.setLastName("Worker");

        cu.setContactInformation(new ContactInformation("worker@realdolmen.com", null, null));

        RegularUser ru = new RegularUser();
        ru.setFirstName("John");
        ru.setLastName("Doe");
        ru.setContactInformation(new ContactInformation("john.doe@gmail.com", null, new Address("Street", "Number", "City", "2942", "Belgium")));

        try {
            partner.setPassword("test");
            cu.setPassword("test");
            ru.setPassword("test");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        entityManager.persist(partner);
        entityManager.persist(cu);
        entityManager.persist(ru);

        Flight flight = new Flight();
        flight.setCreator(partner);
        flight.setDepartureTime(new Date());

        entityManager.persist(flight);
        partner.getOwnedFlights().add(flight);

        entityManager.merge(partner);

        Region region = new Region();
        region.setName("West Europe");

        Airport airportA = new Airport();
        airportA.setName("Zaventem");
        region.getAirports().add(airportA);

        Airport airportB = new Airport();
        airportB.setName("Charleroi");
        region.getAirports().add(airportB);

        entityManager.persist(region);
        airportA.setRegion(region);
        airportB.setRegion(region);
        entityManager.merge(airportA);
        entityManager.merge(airportB);

        Route route = new Route();
        route.setAirportA(airportA);
        route.setAirportB(airportB);

        route.getFlights().add(flight);
        entityManager.persist(route);

        flight.getAvailableSeats().put(FlightClass.FIRST_CLASS, 15);
        flight.getAvailableSeats().put(FlightClass.ECONOMY_CLASS, 10);
        flight.getBasePrices().put(FlightClass.FIRST_CLASS, new BigDecimal(225));
        flight.getBasePrices().put(FlightClass.ECONOMY_CLASS, new BigDecimal(175));
        flight.setRoute(route);
        entityManager.merge(flight);
    }
}

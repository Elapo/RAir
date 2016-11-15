package com.realdolmen.rair.data;

import com.realdolmen.rair.domain.builder.BookingBuilder;
import com.realdolmen.rair.domain.entities.*;
import com.realdolmen.rair.domain.entities.user.CompanyUser;
import com.realdolmen.rair.domain.entities.user.ContactInformation;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.entities.user.RegularUser;
import com.realdolmen.rair.domain.modifiers.CreditCardModifier;
import com.realdolmen.rair.domain.modifiers.MarginModifier;

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
        ContactInformation ci = new ContactInformation("mail@jetair.be", null, new Address("Street", "Number", "City", "2942", "Belgium"));
        partner.setContactInformation(ci);

        Partner partner2 = new Partner();
        partner2.setCompanyName("Ryan Air");
        ContactInformation ci2 = new ContactInformation("mail@ryanair.be", null, new Address("Street", "Number", "City", "2942", "Belgium"));
        partner2.setContactInformation(ci2);

        CompanyUser cu = new CompanyUser();
        cu.setFirstName("Company");
        cu.setLastName("Worker");

        cu.setContactInformation(new ContactInformation("worker@realdolmen.com", null, new Address("Street", "Number", "City", "2942", "Belgium")));

        RegularUser ru = new RegularUser();
        ru.setFirstName("John");
        ru.setLastName("Doe");
        ru.setContactInformation(new ContactInformation("john.doe@gmail.com", null, new Address("Street", "Number", "City", "2942", "Belgium")));

        try {
            partner.setPassword("test");
            partner2.setPassword("test");
            cu.setPassword("test");
            ru.setPassword("test");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        entityManager.persist(partner);
        entityManager.persist(cu);
        entityManager.persist(ru);
        entityManager.persist(partner2);

        Flight flight = new Flight();
        Flight flight2 = new Flight();
        Flight flight3 = new Flight();
        Flight flight4 = new Flight();

        flight.setCreator(partner);
        flight2.setCreator(partner2);
        flight3.setCreator(partner);
        flight4.setCreator(partner2);

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        Date tomorrow2 = new Date(today.getTime() + (1000 * 60 * 60 * 26));
        Date tomorrow3 = new Date(today.getTime() + (1000 * 60 * 60 * 29));
        Date tomorrow4 = new Date(today.getTime() + (1000 * 60 * 60 * 27));

        flight.setDepartureTime(tomorrow);
        flight2.setDepartureTime(tomorrow2);
        flight3.setDepartureTime(tomorrow3);
        flight4.setDepartureTime(tomorrow4);

        Date arr = new Date(today.getTime() + (1000 * 60 * 60 * 30));

        flight.setArrivalTime(arr);
        flight2.setArrivalTime(arr);
        flight3.setArrivalTime(arr);
        flight4.setArrivalTime(arr);

        flight.getAvailableSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight.getAvailableSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight.getAvailableSeats().put(FlightClass.FIRST_CLASS, 15);
        flight.getAvailableSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight.getMaxSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight.getMaxSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight.getMaxSeats().put(FlightClass.FIRST_CLASS, 15);
        flight.getMaxSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight2.getAvailableSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight2.getAvailableSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight2.getAvailableSeats().put(FlightClass.FIRST_CLASS, 15);
        flight2.getAvailableSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight2.getMaxSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight2.getMaxSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight2.getMaxSeats().put(FlightClass.FIRST_CLASS, 15);
        flight2.getMaxSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight3.getAvailableSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight3.getAvailableSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight3.getAvailableSeats().put(FlightClass.FIRST_CLASS, 15);
        flight3.getAvailableSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight3.getMaxSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight3.getMaxSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight3.getMaxSeats().put(FlightClass.FIRST_CLASS, 15);
        flight3.getMaxSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight4.getAvailableSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight4.getAvailableSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight4.getAvailableSeats().put(FlightClass.FIRST_CLASS, 15);
        flight4.getAvailableSeats().put(FlightClass.ECONOMY_CLASS, 10);

        flight4.getMaxSeats().put(FlightClass.BUSINESS_CLASS, 10);
        flight4.getMaxSeats().put(FlightClass.PREMIUM_ECONOMY, 10);
        flight4.getMaxSeats().put(FlightClass.FIRST_CLASS, 15);
        flight4.getMaxSeats().put(FlightClass.ECONOMY_CLASS, 10);

        entityManager.merge(partner);
        entityManager.merge(partner2);

        Region region = new Region();
        region.setName("West Europe");

        Region region2 = new Region();
        region2.setName("United States");

        Region region3 = new Region();
        region3.setName("Middle East");

        entityManager.persist(region2);
        entityManager.persist(region3);

        Airport airportA = new Airport();
        airportA.setName("Zaventem");
        airportA.setAddress(new Address("Street", "10", "CIty", "PostalCode", "Country"));
        region.getAirports().add(airportA);

        Airport airportB = new Airport();
        airportB.setName("Charleroi");
        airportB.setAddress(new Address("Street", "10", "CIty", "PostalCode", "Country"));
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
        route.getFlights().add(flight2);
        route.getFlights().add(flight3);
        route.getFlights().add(flight4);
        entityManager.persist(route);

        flight.setRoute(route);
        flight2.setRoute(route);
        flight3.setRoute(route);
        flight4.setRoute(route);

        flight.getBasePrices().put(FlightClass.FIRST_CLASS, new BigDecimal(225));
        flight.getBasePrices().put(FlightClass.ECONOMY_CLASS, new BigDecimal(175));
        flight.getBasePrices().put(FlightClass.PREMIUM_ECONOMY, new BigDecimal(150));
        flight.getBasePrices().put(FlightClass.BUSINESS_CLASS, new BigDecimal(120));

        flight.getPriceModifiers().add(new CreditCardModifier());
        flight.getPriceModifiers().add(new MarginModifier());

        flight2.getBasePrices().put(FlightClass.FIRST_CLASS, new BigDecimal(225));
        flight2.getBasePrices().put(FlightClass.ECONOMY_CLASS, new BigDecimal(175));
        flight2.getBasePrices().put(FlightClass.PREMIUM_ECONOMY, new BigDecimal(150));
        flight2.getBasePrices().put(FlightClass.BUSINESS_CLASS, new BigDecimal(120));

        flight2.getPriceModifiers().add(new CreditCardModifier());
        flight2.getPriceModifiers().add(new MarginModifier());

        flight3.getBasePrices().put(FlightClass.FIRST_CLASS, new BigDecimal(225));
        flight3.getBasePrices().put(FlightClass.ECONOMY_CLASS, new BigDecimal(175));
        flight3.getBasePrices().put(FlightClass.PREMIUM_ECONOMY, new BigDecimal(150));
        flight3.getBasePrices().put(FlightClass.BUSINESS_CLASS, new BigDecimal(120));

        flight3.getPriceModifiers().add(new CreditCardModifier());
        flight3.getPriceModifiers().add(new MarginModifier());

        flight4.getBasePrices().put(FlightClass.FIRST_CLASS, new BigDecimal(225));
        flight4.getBasePrices().put(FlightClass.ECONOMY_CLASS, new BigDecimal(175));
        flight4.getBasePrices().put(FlightClass.PREMIUM_ECONOMY, new BigDecimal(150));
        flight4.getBasePrices().put(FlightClass.BUSINESS_CLASS, new BigDecimal(120));

        flight4.getPriceModifiers().add(new CreditCardModifier());
        flight4.getPriceModifiers().add(new MarginModifier());


        entityManager.persist(flight);
        entityManager.persist(flight2);
        entityManager.persist(flight3);
        entityManager.persist(flight4);

        partner.getOwnedFlights().add(flight);
        partner2.getOwnedFlights().add(flight2);
        partner.getOwnedFlights().add(flight3);
        partner2.getOwnedFlights().add(flight4);

        entityManager.merge(flight);
        entityManager.merge(flight2);
        entityManager.merge(flight3);
        entityManager.merge(flight4);

        BookingBuilder bookingBuilder1 = new BookingBuilder();
        bookingBuilder1.flight(flight)
                .purchasedOn(new Date())
                .status(BookingStatus.COMPLETE)
                .addModifier(new CreditCardModifier())
                .addModifier(new MarginModifier())
                .paymentMethod(PaymentMethod.CREDIT_CARD);
    }
}
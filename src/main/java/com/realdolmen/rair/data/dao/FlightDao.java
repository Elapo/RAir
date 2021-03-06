package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.entities.user.User;
import org.hibernate.Hibernate;
import org.hibernate.dialect.function.TemplateRenderer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SessionScoped
public class FlightDao extends AbstractDao<Flight, Long> {

    @Inject
    private BookingDao bookingDao;

    @Override
    protected Class<Flight> getDataType() {
        return Flight.class;
    }

    @Override
    public void remove(Flight object) {
        bookingDao.getBookingsByFlight(object).forEach(bookingDao::remove);
        object.getRoute().getFlights().remove(object);
        object.setRoute(null);

        if(object.getCreator() instanceof Partner) {
            Partner partner = (Partner) object.getCreator();
            partner.getOwnedFlights().remove(object);
        }
        super.remove(object);
    }

    public void deactivate(Flight flight) {
        flight.deactivate();
        update(flight);
    }

    public void activate(Flight flight) {
        flight.activate();
        update(flight);
    }

    public List<Flight> getActiveFlights() {
        return em().createNamedQuery("Flight.findAllByState", Flight.class).setParameter("active", true).getResultList();

    }

    @Transactional
    public List<Flight> getAllFlights() {
        List<Flight> flights = em().createNamedQuery("Flight.findAll", Flight.class).getResultList();
        for(Flight flight : flights) {
            Hibernate.initialize(flight.getMaxSeats());
            Hibernate.initialize(flight.getAvailableSeats());
        }
        return flights;
    }

    public List<Flight> getInactiveFlights() {
        return em().createNamedQuery("Flight.findAllByState", Flight.class).setParameter("active", false).getResultList();
    }

    public List<Flight> getAllFlightsByUser(User partner) {
        return em().createNamedQuery("Flight.findAllByPartner", Flight.class).setParameter("creator", partner).getResultList();
    }


    public List<Flight> getFlightsBySearch(Flight flight) throws IllegalAccessException {
        /*StringBuilder jpqlQuery = new StringBuilder("SELECT f FROM Flight f WHERE ");

        List<String> conditions = new ArrayList<>();
        Map<String, Object> parametermap = new HashMap<>();

        Field[] fields = flight.getClass().getDeclaredFields();
        for ( Field f : fields ) {
            boolean accessible = f.isAccessible();
            f.setAccessible(true);
            Object value = f.get(flight);
            f.setAccessible(accessible);
            if ( value != null ) {
                conditions.add("f." + f.getName() + " = :" + f.getName());
                parametermap.put(f.getName(), value);
            }
        }


        if (flight.getDepartureTime() != null) {
            conditions.add("f.departureTime = :depTime");
            parametermap.put("depTime", flight.getDepartureTime());


        for (String condition : conditions) {
            jpqlQuery.append(condition).append(" AND ");
        }

        String result = jpqlQuery.substring(0, jpqlQuery.length() - 5);

        TypedQuery<Flight> qryFlights = em().createQuery(result, Flight.class);

        parametermap.forEach((s, o) -> {
            qryFlights.setParameter(s, o);
        });

        return qryFlights.getResultList();

*/

        StringBuilder jpqlQuery = new StringBuilder("SELECT * FROM Flight f WHERE ");

        List<String> conditions = new ArrayList<>();
        Map<String, Object> parametermap = new HashMap<>();

        if (flight.getDepartureTime() != null) {
            conditions.add("date(f.departureTime) = :depTime");
        }
        /*if ( dateOfArrival       != null ) conditions.add( "f.dateOfArrival = :arrTime"     );
        if ( fromLocation        != null ) conditions.add( "f.fromLocation = :from"         );
        if ( toLocation          != null ) conditions.add( "f.toLocation = :to"             );
        if ( ticketsAdults       > 0     ) conditions.add( "f.ticketsAdults = :adults"      );
        if ( ticketsKids         > 0     ) conditions.add( "f.ticketsKids = :kids"          );
        if ( selectedFlightClass != null ) conditions.add( "f.selectedFlightClass = :class" );
        if ( selectedPartner     != null ) conditions.add( "f.selectedPartner = :class"     );*/


        String result = "";
        if (conditions.size() > 1) {
            for (String condition : conditions) {

                jpqlQuery.append(condition).append(" AND ");
            }
            result = jpqlQuery.substring(0, jpqlQuery.length() - 5);
        } else {
            result = jpqlQuery.append(conditions.get(0)).toString();
        }

        Query qryFlights = em().createNativeQuery(result, Flight.class);
        System.out.println("*********************************" + result);

        /*parametermap.forEach((s, o) -> {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + s + ": " + o);
            qryFlights.setParameter(s, o);

        });*/

        qryFlights.setParameter("depTime", flight.getDepartureTime(), TemporalType.DATE);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + qryFlights);

        return qryFlights.getResultList();
    }
}

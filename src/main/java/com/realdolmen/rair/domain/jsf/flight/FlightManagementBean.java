package com.realdolmen.rair.domain.jsf.flight;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.data.dao.RouteDao;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.FlightClass;
import com.realdolmen.rair.domain.entities.Route;
import com.realdolmen.rair.domain.modifiers.CreditCardModifier;
import com.realdolmen.rair.domain.modifiers.MarginModifier;
import com.realdolmen.rair.domain.modifiers.PriceModifier;
import com.realdolmen.rair.domain.modifiers.VolumeDiscountModifier;
import org.hibernate.Hibernate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class FlightManagementBean implements Serializable {

    @Inject
    private FlightController flightController;

    @Inject
    private AirportDao airportDao;

    private Map<FlightClass, Integer> flightClassMap = new HashMap<>();
    private Airport fromAirport;
    private Airport toAirport;
    private List<Airport> airports = new ArrayList<>();
    private List<PriceModifier> modifiers = new ArrayList<>();
    private List<Class<? extends PriceModifier>> availableModifiers = new ArrayList<>();

    private Class<? extends PriceModifier> selectedNewModifier;

    public Date today() {
        return new Date();
    }

    @Transactional
    public List<Flight> getAllFlights() {
        List<Flight> flights = flightController.getAllFlights();
        flights.forEach(f -> {
            Hibernate.initialize(f.getAvailableSeats());
            Hibernate.initialize(f.getMaxSeats());
        });
        return flights;
    }

    @PostConstruct
    void init() {
        for (FlightClass flightClass : getFlightClasses()) {
            flightClassMap.put(flightClass, 0);
        }

        airports = airportDao.findAll();

        availableModifiers.add(CreditCardModifier.class);

        availableModifiers.add(VolumeDiscountModifier.class);

        //Conditional
        availableModifiers.add(MarginModifier.class);

        modifiers.add(new CreditCardModifier());
        modifiers.add(new VolumeDiscountModifier());
    }

    public Map<FlightClass, Integer> getFlightClassMap() {
        return flightClassMap;
    }

    public void setFlightClassMap(Map<FlightClass, Integer> flightClassMap) {
        this.flightClassMap = flightClassMap;
    }

    public FlightClass[] getFlightClasses() {
        return FlightClass.values();
    }

    public String doAdd() {
        System.out.println(flightClassMap.get(FlightClass.FIRST_CLASS));
        FacesContext.getCurrentInstance().addMessage("addFlight:flightClasses", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wow!", "Super wow"));
        return "addflight";
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public List<Class<? extends PriceModifier>> getAvailableModifiers() {
        return availableModifiers;
    }

    public Class<? extends PriceModifier> getSelectedNewModifier() {
        return selectedNewModifier;
    }

    public void setSelectedNewModifier(Class<? extends PriceModifier> selectedNewModifier) {
        this.selectedNewModifier = selectedNewModifier;
    }

    public void addModifier() {
        try {
            modifiers.add(selectedNewModifier.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<PriceModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<PriceModifier> modifiers) {
        this.modifiers = modifiers;
    }

    public AirportDao getAirportDao() {
        return airportDao;
    }

    public void flightStatusChanged(Flight event) {
        flightController.updateFlightStatus(event);
    }
}
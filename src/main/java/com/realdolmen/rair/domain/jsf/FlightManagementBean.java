package com.realdolmen.rair.domain.jsf;

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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class FlightManagementBean implements Serializable {

    @Inject
    private FlightController flightController;

    @Inject
    private AirportDao airportDao;

    private Map<FlightClass, Integer> flightClassMap = new HashMap<>();

    @NotNull
    private Airport fromAirport;

    @NotNull
    private Airport toAirport;

    private List<Airport> airports = new ArrayList<>();

    private List<PriceModifier> modifiers = new ArrayList<>();

    private List<Class<? extends PriceModifier>> availableModifiers = new ArrayList<>();


    public List<Flight> getAllFlights() {
        return flightController.getAllFlights();
    }

    @PostConstruct
    void init() {
        for (FlightClass flightClass : getFlightClasses()) {
            flightClassMap.put(flightClass, 0);
        }

        airports = airportDao.findAll();

        availableModifiers.add(VolumeDiscountModifier.class);
        availableModifiers.add(CreditCardModifier.class);

        modifiers.add(new CreditCardModifier());
        modifiers.add(new VolumeDiscountModifier());
        modifiers.add(new VolumeDiscountModifier());
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

    private PriceModifier selectedNewModifier;
    public void setSelectedNewModifier(PriceModifier pm) {
        selectedNewModifier = pm;
    }

    public void addModifier() {
        modifiers.add(selectedNewModifier);
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
}

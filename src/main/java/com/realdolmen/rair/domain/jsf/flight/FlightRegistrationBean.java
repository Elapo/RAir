package com.realdolmen.rair.domain.jsf.flight;

import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.controllers.RouteController;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.FlightClass;
import com.realdolmen.rair.domain.modifiers.ModifierPipeline;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named("flightRegistration")
@SessionScoped
public class FlightRegistrationBean implements Serializable {

    private Flight flight;

    private Airport from;
    private Airport to;

    private Class<PriceModifier> selectedModifier;

    private List<PriceModifier> priceModifiers;

    @Inject
    private FlightController flightController;

    @Inject
    private RouteController routeController;

    @PostConstruct
    private void init() {
        reset();
    }

    public String reset() {
        flight = new Flight();
        Arrays.stream(FlightClass.values()).forEach(c -> {
            flight.getAvailableSeats().put(c, 0);
            flight.getMaxSeats().put(c, 0);
            flight.getBasePrices().put(c, new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        });

        priceModifiers = new ArrayList<>();

        return null;
    }

    public String registerFlight() {
        flight.getMaxSeats().forEach(flight.getAvailableSeats()::put);

        flightController.registerFlight(from, to, flight);
        reset();
        return "dashFlights";
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public Class<PriceModifier> getSelectedModifier() {
        return selectedModifier;
    }

    public void setSelectedModifier(Class<PriceModifier> selectedModifier) {
        this.selectedModifier = selectedModifier;
    }

    public void addModifier() {
        if(selectedModifier != null) {
            try {
                priceModifiers.add(selectedModifier.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                FacesContext.getCurrentInstance().addMessage("modifierForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot add!", "The modifier '" + selectedModifier.getSimpleName() + "' cannot be instantiated!"));
            }
        }
    }

    public void removeModifier(PriceModifier modifier) {
        priceModifiers.remove(modifier);
    }

    public List<PriceModifier> getPriceModifiers() {
        return priceModifiers;
    }

    public void setPriceModifiers(List<PriceModifier> priceModifiers) {
        this.priceModifiers = priceModifiers;
    }
}

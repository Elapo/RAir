package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.FlightClass;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Named("flightRegistration")
@SessionScoped
public class FlightRegistrationBean implements Serializable {

    private Map<FlightClass, Integer> seatsByClass = new HashMap<>();
    private Map<FlightClass, BigDecimal> priceByClass = new HashMap<>();

    private Flight flight;

    @PostConstruct
    private void init() {
        reset();
    }

    public void reset() {
        flight = new Flight();
        Arrays.stream(FlightClass.values()).forEach(c -> {
            seatsByClass.put(c, 0);
            priceByClass.put(c, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP));
        });
    }

    public String registerFlight() {
        return null;
    }

    public Map<FlightClass, Integer> getSeatsByClass() {
        return seatsByClass;
    }

    public void setSeatsByClass(Map<FlightClass, Integer> seatsByClass) {
        this.seatsByClass = seatsByClass;
    }

    public Map<FlightClass, BigDecimal> getPriceByClass() {
        return priceByClass;
    }

    public void setPriceByClass(Map<FlightClass, BigDecimal> priceByClass) {
        this.priceByClass = priceByClass;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}

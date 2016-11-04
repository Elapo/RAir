package com.realdolmen.rair.domain.entities;

import com.realdolmen.rair.domain.FlightClass;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIME)
    private Date departureTime;

    //TODO: Flight duration: Do or don't?

    @ManyToOne
    private Partner creator;

    @ManyToOne
    private Route route;

    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name="flight_seats")
    @Column(name = "available_seats")
    private Map<FlightClass, Integer> availableSeats;

    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name="flight_class_base_price")
    @Column(name = "base_price")
    private Map<FlightClass, BigDecimal> basePrices;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Partner getCreator() {
        return creator;
    }

    public void setCreator(Partner creator) {
        this.creator = creator;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Map<FlightClass, Integer> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Map<FlightClass, Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Map<FlightClass, BigDecimal> getBasePrices() {
        return basePrices;
    }

    public void setBasePrices(Map<FlightClass, BigDecimal> basePrices) {
        this.basePrices = basePrices;
    }
}

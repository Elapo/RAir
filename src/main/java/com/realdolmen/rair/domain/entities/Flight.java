package com.realdolmen.rair.domain.entities;

import com.realdolmen.rair.data.dao.Toggle;
import com.realdolmen.rair.domain.entities.user.Partner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@NamedQueries({
        @NamedQuery(name = "Flight.findAll", query = "SELECT f FROM Flight f"),
        @NamedQuery(name = "Flight.findAllByState", query = "SELECT f FROM Flight f WHERE f.active = :active")
})
public class Flight implements Toggle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Partner creator;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Route route;

    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name = "flight_seats")
    @Column(name = "available_seats")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<FlightClass, Integer> availableSeats = new HashMap<>();

    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name = "flight_class_base_price")
    @Column(name = "base_price")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<FlightClass, BigDecimal> basePrices = new HashMap<>();

    private Boolean active = true;

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

    @Override
    public void activate() {
        active = true;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deactivate() {
        active = false;
    }
}

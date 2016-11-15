package com.realdolmen.rair.domain.entities;

import com.realdolmen.rair.data.dao.Toggle;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Flight.findAll", query = "SELECT f FROM Flight f"),
        @NamedQuery(name = "Flight.findAllByState", query = "SELECT f FROM Flight f WHERE f.active = :active"),
        @NamedQuery(name = "Flight.findAllByPartner", query = "SELECT f FROM Flight f WHERE f.creator = :creator")

})
public class Flight implements Toggle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date departureTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Partner creator;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Route route;

    @NotNull
    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name = "flight_available_seats")
    @Column(name = "available_seats")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<FlightClass, Integer> availableSeats = new HashMap<>();

    @NotNull
    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name = "flight_max_seats")
    @Column(name = "max_seats")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<FlightClass, Integer> maxSeats = new HashMap<>();

    @NotNull
    @ElementCollection
    @MapKeyColumn(name = "class")
    @JoinTable(name = "flight_class_base_price")
    @Column(name = "base_price")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<FlightClass, BigDecimal> basePrices = new HashMap<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PriceModifier> priceModifiers = new ArrayList<>();

    private Boolean active = true;

    @Version
    private Integer version = 0;

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

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    @Override
    public void setActive(boolean flag) {
        active = flag;
    }

    public List<PriceModifier> getPriceModifiers() {
        return priceModifiers;
    }

    public void setPriceModifiers(List<PriceModifier> priceModifiers) {
        this.priceModifiers = priceModifiers;
    }

    public Map<FlightClass, Integer> getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Map<FlightClass, Integer> maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != null ? !id.equals(flight.id) : flight.id != null) return false;
        if (availableSeats != null ? !availableSeats.equals(flight.availableSeats) : flight.availableSeats != null)
            return false;
        if (maxSeats != null ? !maxSeats.equals(flight.maxSeats) : flight.maxSeats != null) return false;
        if (basePrices != null ? !basePrices.equals(flight.basePrices) : flight.basePrices != null) return false;
        return priceModifiers != null ? priceModifiers.equals(flight.priceModifiers) : flight.priceModifiers == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (availableSeats != null ? availableSeats.hashCode() : 0);
        result = 31 * result + (maxSeats != null ? maxSeats.hashCode() : 0);
        result = 31 * result + (basePrices != null ? basePrices.hashCode() : 0);
        result = 31 * result + (priceModifiers != null ? priceModifiers.hashCode() : 0);
        return result;
    }
}

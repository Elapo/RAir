package com.realdolmen.rair.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Route.findByAirport", query = "SELECT r FROM Route r WHERE r.airportA = :airport OR r.airportB = :airport"),
        @NamedQuery(name = "Route.findByStrictAirports", query = "SELECT r FROM Route r WHERE r.airportA = :from AND r.airportB = :to")
})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Flight> flights = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Airport airportA;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Airport airportB;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Airport getAirportA() {
        return airportA;
    }

    public void setAirportA(Airport airportA) {
        this.airportA = airportA;
    }

    public Airport getAirportB() {
        return airportB;
    }

    public void setAirportB(Airport airportB) {
        this.airportB = airportB;
    }
}

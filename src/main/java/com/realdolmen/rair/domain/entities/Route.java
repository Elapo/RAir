package com.realdolmen.rair.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Route.findByAirport", query = "SELECT r FROM Route r WHERE r.airportA = :airport OR r.airportB = :airport")
})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Flight> flights;

    @ManyToOne
    private Airport airportA;

    @ManyToOne
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

package com.realdolmen.rair.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
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
}

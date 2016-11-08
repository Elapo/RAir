package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Airport;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AirportDao extends AbstractDao<Airport, Long> {
    @Override
    protected Class<Airport> getDataType() {
        return Airport.class;
    }
}

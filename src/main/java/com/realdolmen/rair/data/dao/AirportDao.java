package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Airport;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AirportDao extends AbstractDao<Airport, Long> {
    @Override
    protected Class<Airport> getDataType() {
        return Airport.class;
    }

    public Airport findAirportByName(String name) {
        List<Airport> lst = super.em().createNamedQuery("Airport.FindByName", Airport.class).setParameter("name", name).getResultList();

        if ( !lst.isEmpty() ) {
            return lst.get(0);
        }

        return null;
    }

    public List<Airport> findAllAirportLike(String name) {
        return  super.em().createNamedQuery("Airport.FindLikeName").setParameter("name", name).getResultList();
    }
}

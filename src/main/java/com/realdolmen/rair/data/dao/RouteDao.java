package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Route;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import java.util.List;

@ApplicationScoped
public class RouteDao extends AbstractDao<Route, Long> {
    @Override
    protected Class<Route> getDataType() {
        return Route.class;
    }

    public List<Route> getRoutesByAirport(Airport airport) {
        return em().createNamedQuery("Route.findByAirport", Route.class).getResultList();
    }

    public Route getRouteByAirports(Airport from, Airport to) {
        try {
            return em().createNamedQuery("Route.findByStrictAirports", Route.class).setParameter("from", from).setParameter("to", to).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}

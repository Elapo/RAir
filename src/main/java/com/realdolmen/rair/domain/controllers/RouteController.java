package com.realdolmen.rair.domain.controllers;

import com.realdolmen.rair.data.dao.RouteDao;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Route;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class RouteController extends AbstractController {

    @Inject
    private RouteDao routeDao;

    public Route getRouteByAirports(Airport from, Airport to) {
        return routeDao.getRouteByAirports(from, to);
    }

    @Transactional
    public Route createRoute(Airport from, Airport to) {
        Route route = new Route();
        route.setAirportA(routeDao.em().find(Airport.class, from.getId()));
        route.setAirportB(routeDao.em().find(Airport.class, to.getId()));
        routeDao.insert(route);
        return routeDao.find(route.getId());
    }

    public Route update(Route route) {
        return routeDao.update(route);
    }
}

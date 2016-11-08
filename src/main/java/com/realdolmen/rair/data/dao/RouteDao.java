package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Route;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RouteDao extends AbstractDao<Route, Long> {
    @Override
    protected Class<Route> getDataType() {
        return Route.class;
    }
}

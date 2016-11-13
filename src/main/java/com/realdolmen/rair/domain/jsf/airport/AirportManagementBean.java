package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Airport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AirportManagementBean {

    @Inject
    private AirportDao airportDao;

    public List<Airport> getAllAirports() {
        return airportDao.findAll();
    }
}

package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Address;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Region;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AirportRegistrationBean {


    private Airport airport;

    private Address address;

    private Region region;

    @Inject
    private AirportDao airportDao;

    @PostConstruct
    private void init() {
        reset();
    }

    public void reset() {
        airport = new Airport();
        address = new Address();
        region = null;
    }

    public void registerAirport() {
        airport.setAddress(address);
        airport.setRegion(region);
        airportDao.insert(airport);
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

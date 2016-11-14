package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Address;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Region;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

@Named
@RequestScoped
public class AirportRegistrationBean {


    @Valid
    private Airport airport;

    @Valid
    private Address address;

    @Valid
    private Region region;

    @Inject
    private AirportDao airportDao;

    @Inject
    private AirportManagementBean airportManagementBean;

    @PostConstruct
    private void init() {
        reset();
    }

    public void reset() {
        airport = new Airport();
        address = new Address();
        region = null;
    }

    public String registerAirport() {
        airport.setAddress(address);
        airport.setRegion(region);
        airportDao.insert(airport);
        airportManagementBean.reset();
        return "dashAirports";
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}

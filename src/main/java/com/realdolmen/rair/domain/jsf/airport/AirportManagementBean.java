package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.data.dao.RegionDao;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Region;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AirportManagementBean implements Serializable {

    @Inject
    private AirportDao airportDao;

    @Inject
    private RegionDao regionDao;

    private List<Airport> allAirports;

    public List<Airport> getAllAirports() {
        if (allAirports == null) {
            allAirports = airportDao.findAll();
        }
        return allAirports;
    }

    public void airportChanged(Airport airport) {
        airportDao.update(airport);
        reset();
    }

    public void reset() {
        allAirports = airportDao.findAll();
    }

    public List<Region> getRegions() {
        return regionDao.findAll();
    }

    public RegionDao getRegionDao() {
        return regionDao;
    }
}

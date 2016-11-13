package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Airport;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AirportDetailsBean {

    private long airportId;

    private Airport airport;

    @Inject
    private AirportDao airportDao;

    public long getAirportId() {
        return airportId;
    }

    public void setAirportId(long airportId) {
        this.airportId = airportId;
    }

    public void load() {
        airport = airportDao.find(airportId);
        if (airport == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Flight '" + airportId + "' is not found!"));
        }
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

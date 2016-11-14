package com.realdolmen.rair.domain.jsf.airport;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.entities.Region;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AirportDetailsBean implements Serializable {

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

    public void nameChanged(ValueChangeEvent event) {
        System.out.println(event.getNewValue());
        airport.setName((String) event.getNewValue());
        updateAirport();
    }

    public void regionChanged(ValueChangeEvent event) {
        System.out.println(event.getNewValue());
        airport.setRegion((Region) event.getNewValue());
        updateAirport();
    }

    public void updateAirport() {
        airportDao.update(airport);
    }
}

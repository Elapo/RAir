package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Flight;
import org.hibernate.Hibernate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;


@Named(value = "details")
@SessionScoped
public class DetailsBean implements Serializable{

    //region CONSTANTS -

    //endregion

    //region Injects +

    @Inject
    private FlightDao flightDao;

    @Inject
    private FlightController controller;

    //endregion

    //region Private Member Variables +

    private Long selectedId;

    private Flight selectedFlight;

    //endregion

    //region Private Properties -



    //endregion

    //region Private Methods -

    //endregion

    //region Constructors +

    public DetailsBean() {}

    //endregion

    //region Public Properties +

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public void getSelectedFlightFromDAO() {
        this.selectedFlight = flightDao.find(selectedId);
    }

    //endregion

    //region Public Methods +

    private void fixLazyInit() {
        Hibernate.initialize(selectedFlight.getBasePrices());
        Hibernate.initialize(selectedFlight.getAvailableSeats());
        Hibernate.initialize(selectedFlight.getCreator().getOwnedFlights());
    }


    @PostConstruct
    @Transactional
    public void details() {
        selectedFlight = controller.getFlight(selectedId);
        fixLazyInit();
        if(selectedFlight == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Flight '" + selectedId + "' is not found!"));
        }
    }

    //endregion

}



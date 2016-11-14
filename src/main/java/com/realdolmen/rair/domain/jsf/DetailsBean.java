package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.builder.BookingBuilder;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.Ticket;
import com.realdolmen.rair.domain.modifiers.ModifierPipeline;
import com.realdolmen.rair.domain.modifiers.PriceModifier;
import org.hibernate.Hibernate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;


@Named(value = "details")
@SessionScoped
public class DetailsBean implements Serializable{

    //region CONSTANTS -

    //endregion

    //region Injects +

    @Inject
    private FlightController controller;

    @Inject
    private SessionBean sessionBean;

    @Inject
    private SearchBean searchBean;

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

    public BigDecimal calculatePrice() {
        BookingBuilder builder = new BookingBuilder();
        builder.flight(selectedFlight);

        for(PriceModifier modifier : selectedFlight.getPriceModifiers()) {
            builder.addModifier(modifier);
        }

        int kids = 0;
        if(searchBean.getTicketsKids() != null) {
            kids = searchBean.getTicketsKids();
        }
        for(int i = 0; i < searchBean.getTicketsAdults() + kids; i++) {
            Ticket ticket = new Ticket();
            builder.addTicket(ticket);
        }
        ModifierPipeline pricePipeline = ModifierPipeline.loadIntoOrder(selectedFlight.getPriceModifiers());

        BigDecimal basePrice = selectedFlight.getBasePrices().get(searchBean.getSelectedFlightClass());
        return pricePipeline.pass(basePrice, builder.build());
    }

    //endregion

    //region Public Methods +

    private void fixLazyInit() {
        Hibernate.initialize(selectedFlight.getBasePrices());
        Hibernate.initialize(selectedFlight.getAvailableSeats());
        Hibernate.initialize(selectedFlight.getMaxSeats());
        Hibernate.initialize(selectedFlight.getCreator());
        Hibernate.initialize(selectedFlight.getCreator().getOwnedFlights());
        Hibernate.initialize(selectedFlight.getRoute());
        Hibernate.initialize(selectedFlight.getPriceModifiers());
    }

    @Transactional
    public void details() {
        if (selectedId != null) {
            selectedFlight = controller.getFlight(selectedId);
            if (selectedFlight != null) {
                fixLazyInit();
                return;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Flight '" + selectedId + "' is not found!", null));
    }

    public String book() {
        if ( !sessionBean.isLoggedIn() ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"You need to log in before booking.", null));
            return null;
        }

        return "pretty:booking";
    }

    //endregion

}



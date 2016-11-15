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
import javax.validation.constraints.*;
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

    @Size(min = 16, max = 16)
    @Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$", message = "- Not a valid visa cardnumber.")
    private String cc;

    @Size(min = 5, max = 5)
    @Pattern(regexp = "/^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$/;", message = "- Not a valid expire date.")
    private String ccd;

    private String paymentMethod;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods -

    //endregion

    //region Constructors +

    public DetailsBean() {}

    //endregion

    //region Public Properties +


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCcd() {
        return ccd;
    }

    public void setCcd(String ccd) {
        this.ccd = ccd;
    }

    //endregion

    //region Public Methods +

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

            return "login?faces-redirect=true&page=payment";
        }
        return "WEB-INF/views/payment.xhtml";
    }

    //endregion

}



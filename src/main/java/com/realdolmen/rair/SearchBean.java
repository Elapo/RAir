package com.realdolmen.rair;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named(value = "search")
@SessionScoped
public class SearchBean implements Serializable {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    private Date dateOfDeparture;
    private Date dateOfArrival;
    private Integer ticketsAdults;
    private Integer ticketsKids;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods -

    //endregion

    //region Constructors -

    //endregion

    //region Public Properties +

    public Integer getTicketsAdults() {
        return ticketsAdults;
    }

    public void setTicketsAdults(Integer ticketsAdults) {
        this.ticketsAdults = ticketsAdults;
    }

    public Integer getTicketsKids() {
        return ticketsKids;
    }

    public void setTicketsKids(Integer ticketsKids) {
        this.ticketsKids = ticketsKids;
    }

    public Date getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(Date dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Date getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Date dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    //endregion

    //region Public Methods +

    public void search() {
        //TODO: formvalidatie
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
    }

    //endregion
}

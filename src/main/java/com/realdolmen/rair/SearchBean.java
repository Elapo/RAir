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

    private Date dateOfDeparture;
    private Date dateOfArrival;


    private int ticketsAdults;

    private int ticketsKids;

    public int getTicketsAdults() {
        return ticketsAdults;
    }

    public void setTicketsAdults(int ticketsAdults) {
        this.ticketsAdults = ticketsAdults;
    }

    public int getTicketsKids() {
        return ticketsKids;
    }

    public void setTicketsKids(int ticketsKids) {
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



    public void search() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
    }
}

package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.domain.controllers.BookingController;
import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "profile")
@SessionScoped
public class ProfileBean implements Serializable {

    //region CONSTANTS -

    //endregion

    //region Injects +

    @Inject
    private SessionBean sessionBean;

    @Inject
    BookingController bookingController;

    //endregion

    //region Private Member Variables +

    private List<Booking> lstBookings;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods +

    private List<Booking> getBookingsOfUser() {
        return bookingController.getBookingsByUser(sessionBean.getAuthorizer().getUser());
    }

    //endregion	

    //region Constructors +

    public ProfileBean() {}

    //endregion

    //region Public Properties +

    public List<Booking> getLstBookings() {
        return lstBookings;
    }

    public void setLstBookings(List<Booking> lstBookings) {
        this.lstBookings = lstBookings;
    }

    //endregion

    //region Public Methods -

    public void setup() {
        this.lstBookings = getBookingsOfUser();
    }

    //endregion

}

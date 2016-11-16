package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.dao.BookingDao;
import com.realdolmen.rair.domain.controllers.BookingController;
import com.realdolmen.rair.domain.entities.Booking;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named(value = "booking")
@RequestScoped
public class BookingBean implements Serializable {

    //region CONSTANTS -

    //endregion

    //region Injects +

    @Inject
    private BookingDao bookingDao;

    //endregion

    //region Private Member Variables +

    private Long bookId;

    private Booking booking;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods -

    //endregion

    //region Constructors +

    public BookingBean() {}

    //endregion

    //region Public Properties +

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    //endregion

    //region Public Methods +

    public void loadBooking() {
        try {
            this.booking = bookingDao.find(bookId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //endregion

}

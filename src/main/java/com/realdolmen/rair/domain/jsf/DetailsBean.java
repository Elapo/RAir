package com.realdolmen.rair.domain.jsf;


import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.builder.BookingBuilder;
import com.realdolmen.rair.domain.controllers.BookingController;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.*;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.modifiers.ModifierPipeline;
import com.realdolmen.rair.domain.modifiers.PriceModifier;
import org.hibernate.Hibernate;
import org.hibernate.StaleObjectStateException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.*;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;


@Named(value = "details")
@SessionScoped
public class DetailsBean implements Serializable {

    //region CONSTANTS -

    //endregion

    //region Injects +

    @Inject
    private FlightController controller;

    @Inject
    private SessionBean sessionBean;

    @Inject
    private SearchBean searchBean;

    @Inject
    private BookingController bookingController;

    @Inject
    private FlightDao flightDao;

    //endregion

    //region Private Member Variables +

    private Long selectedId;

    private Flight selectedFlight;

    @Size(min = 19, max = 19)
    private String cc;

    @Size(min = 5, max = 5)
    private String ccd;

    private PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

    private Booking booking;

    private String urlForQR;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods -

    //endregion

    //region Constructors +

    public DetailsBean() {
    }

    //endregion

    //region Public Properties +


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentMethod[] getPaymentMethods() {
        return PaymentMethod.values();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getUrlForQR() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        URI uri = URI.create(req.getRequestURL().toString());
        return uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
    }

    //endregion

    //region Public Methods +

    public BigDecimal calculatePrice(boolean includePaymentMethod) {
        BookingBuilder builder = new BookingBuilder();
        builder.flight(selectedFlight);
        if (includePaymentMethod)
            builder.paymentMethod(paymentMethod);
        else
            builder.paymentMethod(null);

        for (PriceModifier modifier : selectedFlight.getPriceModifiers()) {
            builder.addModifier(modifier);
        }

        int kids = 0;
        if (searchBean.getTicketsKids() != null) {
            kids = searchBean.getTicketsKids();
        }
        for (int i = 0; i < searchBean.getTicketsAdults() + kids; i++) {
            Ticket ticket = new Ticket();
            builder.addTicket(ticket);
        }

        builder.flightClass(searchBean.getSelectedFlightClass());
        ModifierPipeline pricePipeline = ModifierPipeline.loadIntoOrder(selectedFlight.getPriceModifiers());

        BigDecimal basePrice = selectedFlight.getBasePrices().get(searchBean.getSelectedFlightClass());
        if (searchBean.getTicketsKids() != null)
            return pricePipeline.pass(basePrice, builder.build()).multiply(new BigDecimal(searchBean.getTicketsAdults() + (searchBean.getTicketsKids())));
        return pricePipeline.pass(basePrice, builder.build()).multiply(new BigDecimal(searchBean.getTicketsAdults()));
    }

    private void fixLazyInit() {
        Hibernate.initialize(selectedFlight.getBasePrices());
        Hibernate.initialize(selectedFlight.getAvailableSeats());
        Hibernate.initialize(selectedFlight.getMaxSeats());
        Hibernate.initialize(selectedFlight.getCreator());
        if (selectedFlight.getCreator() instanceof Partner) {
            Hibernate.initialize(((Partner) selectedFlight.getCreator()).getOwnedFlights());
        }
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Flight '" + selectedId + "' is not found!", null));
    }

    public String book() {
        if (!sessionBean.isLoggedIn()) {

            return "login?faces-redirect=true&page=payment";
        }
        return "pretty:payment";
    }

    public String tyContinue() {
        return "pretty:profile";
    }

    public void reserveSeats() {
        int seats = selectedFlight.getAvailableSeats().get(searchBean.getSelectedFlightClass());

        int numberOfTickets = searchBean.getTicketsAdults();
        if (searchBean.getTicketsKids() != null) numberOfTickets += searchBean.getTicketsKids();
        selectedFlight.getAvailableSeats().put(searchBean.getSelectedFlightClass(), seats - numberOfTickets);
    }

    @Transactional
    public String pay() {
        BookingBuilder b = new BookingBuilder();

        int tickets = 0;
        if (searchBean.getTicketsKids() != null) {
            tickets = searchBean.getTicketsKids();
        }

        tickets += searchBean.getTicketsAdults();

        Ticket t;
        for (int i = 0; i < tickets; i++) {
            t = new Ticket();
            t.setFlightClass(searchBean.getSelectedFlightClass());
            b.addTicket(t);
        }

        b.purchasedOn(new Date());
        b.flight(selectedFlight);
        b.flightClass(searchBean.getSelectedFlightClass());

        if (paymentMethod == PaymentMethod.CREDIT_CARD) b.status(BookingStatus.COMPLETE);
        else b.status(BookingStatus.PENDING);

        selectedFlight.getPriceModifiers().forEach(priceModifier -> {
            priceModifier.setId(null);
            b.addModifier(priceModifier);
        });

        booking = b.build();

        booking.setPaymentMethod(paymentMethod);

        reserveSeats();

        try {
            flightDao.update(selectedFlight);
            bookingController.registerBooking(booking);
        } catch (StaleObjectStateException | OptimisticLockException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The data you have is out of date.", null));
            return null;
        }

        return "/WEB-INF/views/thankyou.xhtml";
    }

    //endregion

}



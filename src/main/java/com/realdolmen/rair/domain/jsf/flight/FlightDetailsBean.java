package com.realdolmen.rair.domain.jsf.flight;

import com.realdolmen.rair.data.dao.BookingDao;
import com.realdolmen.rair.data.dao.FlightDao;
import com.realdolmen.rair.domain.AuthLevel;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.jsf.SessionBean;
import com.realdolmen.rair.domain.modifiers.MarginModifier;
import com.realdolmen.rair.domain.modifiers.ModifierPipeline;
import com.realdolmen.rair.domain.modifiers.PriceModifier;
import org.hibernate.Hibernate;
import org.hibernate.StaleObjectStateException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FlightDetailsBean implements Serializable {

    private long flightId;

    private Flight flight;

    @Inject
    private FlightDao flightDao;

    @Inject
    private FlightController controller;

    private List<Booking> bookingList = new ArrayList<>();

    private Class<PriceModifier> selectedModifier;

    @Inject
    private BookingDao bookingDao;

    @Inject
    private FlightManagementBean flightManagementBean;

    @Inject
    private SessionBean sessionBean;

    private List<PriceModifier> filteredModifiers = new ArrayList<>();

    @Transactional
    public void load() {
        flight = controller.getFlight(flightId);
        if (flight == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Flight '" + flightId + "' is not found!"));
            return;
        }

        flightManagementBean.resetModifiers();
        Hibernate.initialize(flight.getPriceModifiers());
        Hibernate.initialize(flight.getBasePrices());
        Hibernate.initialize(flight.getMaxSeats());

        bookingList = bookingDao.getBookingsByFlight(flight);
        filterModifiers();
    }

    public void filterModifiers() {
        filteredModifiers = flight.getPriceModifiers().stream().filter(m -> {
            if (m instanceof MarginModifier) {
                if (sessionBean.isLoggedIn() && !sessionBean.getAuthorizer().checkCompanyUser(AuthLevel.ALLOWED)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public Class<PriceModifier> getSelectedModifier() {
        return selectedModifier;
    }

    public void setSelectedModifier(Class<PriceModifier> selectedModifier) {
        this.selectedModifier = selectedModifier;
    }

    public void removeModifier(PriceModifier modifier) {
        filteredModifiers.remove(modifier);
        updateFlight();
    }

    public void updateFlight() {
        if (flight == null) {
            FacesContext.getCurrentInstance().addMessage("editFlight", new FacesMessage("Unable to load the flight so updating is impossible!"));
            return;
        }

        try {
            List<MarginModifier> marginModifiers = flight.getPriceModifiers().stream().filter(m -> m instanceof MarginModifier).map(v -> (MarginModifier) v).collect(Collectors.toList());
            List<PriceModifier> allModifiers = new ArrayList<>();

            for (PriceModifier m : filteredModifiers) {
                allModifiers.add(m);
            }

            for (PriceModifier m : marginModifiers) {
                if(!allModifiers.contains(m)) {
                    allModifiers.add(m);
                }
            }

            ModifierPipeline p = ModifierPipeline.loadIntoOrder(allModifiers);
            flight.setPriceModifiers(p.getOrderedModifiers());
            flightDao.update(flight);
            filterModifiers();
        } catch (StaleObjectStateException | OptimisticLockException ex) {
            FacesContext.getCurrentInstance().addMessage("editFlight", new FacesMessage("Object was updated, please review and try again!"));
        }
    }

    public List<PriceModifier> getFilteredModifiers() {
        return filteredModifiers;
    }

    public void setFilteredModifiers(List<PriceModifier> filteredModifiers) {
        this.filteredModifiers = filteredModifiers;
    }

    public String reset() {
        return "dashFlights";
    }

    public void addModifier() {
        if (selectedModifier != null) {
            try {
                filteredModifiers.add(selectedModifier.newInstance());
                updateFlight();
            } catch (InstantiationException | IllegalAccessException e) {
                FacesContext.getCurrentInstance().addMessage("modifierForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot add!", "The modifier '" + selectedModifier.getSimpleName() + "' cannot be instantiated!"));
            }
        }
    }
}

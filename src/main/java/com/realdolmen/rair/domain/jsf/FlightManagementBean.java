package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.dao.RouteDao;
import com.realdolmen.rair.domain.controllers.FlightController;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.domain.entities.FlightClass;
import com.realdolmen.rair.domain.entities.Route;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class FlightManagementBean implements Serializable {

    @Inject
    private FlightController flightController;

    @Inject
    private RouteDao routeDao;

    private Map<FlightClass, Integer> flightClassMap = new HashMap<>();

    private Route selectedRoute;

    public List<Flight> getAllFlights() {
        return flightController.getAllFlights();
    }

    @PostConstruct
    void init() {
        for(FlightClass flightClass : getFlightClasses()) {
            flightClassMap.put(flightClass, 0);
        }
    }

    public Map<FlightClass, Integer> getFlightClassMap() {
        return flightClassMap;
    }

    public void setFlightClassMap(Map<FlightClass, Integer> flightClassMap) {
        this.flightClassMap = flightClassMap;
    }

    public FlightClass[] getFlightClasses() {
        return FlightClass.values();
    }

    public List<Route> getRoutes() {
        return routeDao.findAll();
    }

    public String doAdd() {
        System.out.println(flightClassMap.get(FlightClass.FIRST_CLASS));
        FacesContext.getCurrentInstance().addMessage("addFlight:flightClasses", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wow!", "Super wow"));
        return "addflight";
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }
}

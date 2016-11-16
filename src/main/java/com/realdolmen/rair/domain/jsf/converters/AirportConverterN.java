package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.data.dao.AirportDao;
import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.jsf.flight.FlightManagementBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named(value = "airportConverter")
public class AirportConverterN implements Converter {

    @Inject
    private FlightManagementBean flightManagementBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && !s.equals("")) {
            return flightManagementBean.getAirportDao().findAirportByName(s);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o == null) {
            return "Airport IS NULL";
        }

        if(o instanceof Airport) {
            return ((Airport) o).getName();
        }

        return o.toString();
    }
}

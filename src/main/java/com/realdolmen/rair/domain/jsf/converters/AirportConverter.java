package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.domain.entities.Airport;
import com.realdolmen.rair.domain.jsf.flight.FlightManagementBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

//@FacesConverter("com.realdolmen.convert.Airport")
@Named
public class AirportConverter implements Converter {

    @Inject
    private FlightManagementBean flightManagementBean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null) {
            return null;
        }

        try {
            long id = Long.parseLong(s);
            return flightManagementBean.getAirportDao().find(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o == null) {
            return "Airport IS NULL";
        }

        if(o instanceof Airport) {
            return ((Airport) o).getId().toString();
        }
        return o.toString();
    }
}

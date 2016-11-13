package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.data.dao.RegionDao;
import com.realdolmen.rair.domain.entities.Region;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RegionConverter implements Converter {

    @Inject
    private RegionDao regionDao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null) {
            throw new ConverterException("String should not be null!");
        }

        try {
            long id = Long.parseLong(s);
            return regionDao.find(id);
        } catch (NumberFormatException nfe) {
            throw new ConverterException("'" + s + "' was not a valid long ID", nfe);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        if (o == null) {
            throw new ConverterException("Object should not be null!");
        }

        if (o instanceof Region) {
            return ((Region) o).getId().toString();
        }

        throw new ConverterException("Object is not a valid region instance!");
    }
}

package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.jsf.SearchBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "partnerConverter")
public class PartnerConverter implements Converter {

    @Inject
    private UserDao userDao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println(value);
        if (value == null) {
            System.out.println("nullllll");
            return null;
        }
        try {
            return userDao.find(Partner.class, Long.valueOf(value));
        } catch (Exception e) {
            System.out.println("EXCEPTIONNNNNNNN");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "PARTNER IS NULL";
        }

        if (o instanceof Partner) return o.toString();

        return o.toString();
    }


}

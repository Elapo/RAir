package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.jsf.SearchBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named(value = "partnerConverter")
public class PartnerConverter implements Converter {

    @Inject
    private UserDao userDao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null && value.equals("")) {
            return null;
        }
        try {
            return userDao.find(Partner.class, Long.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "-42";
        }

        if (o instanceof Partner) return ((Partner) o).getId().toString();

        return null;
    }


}

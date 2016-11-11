package com.realdolmen.rair.domain.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
public class CurrencyConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return new BigDecimal(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((BigDecimal) o).toString();
    }
}

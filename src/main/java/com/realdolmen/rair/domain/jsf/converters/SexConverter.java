package com.realdolmen.rair.domain.jsf.converters;


import com.realdolmen.rair.Sex;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Arrays;
import java.util.stream.Stream;


//@FacesConverter(forClass = Sex.class)
public class SexConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return Sex.valueOf(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }

}

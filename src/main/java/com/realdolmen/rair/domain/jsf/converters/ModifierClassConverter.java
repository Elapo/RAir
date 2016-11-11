package com.realdolmen.rair.domain.jsf.converters;

import com.realdolmen.rair.domain.modifiers.PriceModifier;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.realdolmen.convert.Class")
public class ModifierClassConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null) {
            return null;
        }
        try {
            return (Class<PriceModifier>) getClass().getClassLoader().loadClass(s);
        } catch (ClassNotFoundException e) {
            throw new ConverterException("The class '" + s + "' is not found in the class loader!");
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "CLASS IS NULL";
        }

        Class<? extends PriceModifier> c = (Class<PriceModifier>) o;
        return c.getCanonicalName();
    }
}

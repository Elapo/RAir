package com.realdolmen.rair;

import java.io.Serializable;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.core.Cookie;
import javax.enterprise.context.SessionScoped;

@Named(value = "language")
@SessionScoped
public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    public LanguageBean() {
        this.locale = new Locale("en", "GB");
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String changeLocale(String code1, String code2){
        this.locale = new Locale(code1, code2);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
        return "index?faces-redirect=true";
    }
}

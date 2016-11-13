package com.realdolmen.rair.domain.jsf;

import java.io.Serializable;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.core.Cookie;
import javax.enterprise.context.SessionScoped;

@Named(value = "language")
@SessionScoped
public class LanguageBean implements Serializable {

    //region CONSTANTS +

    private static final long serialVersionUID = 1L;

    //endregion

    //region Private Member Variables +

    private Locale locale;

    //endregion

    //region Private Properties -

    //endregion

    //region Private Methods -

    //endregion

    //region Constructors +

    public LanguageBean() {
        this.locale = new Locale("en", "GB");
    }

    //endregion

    //region Public Properties +

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    //endregion

    //region Public Methods +

    public void changeLocale(String code1, String code2){
        this.locale = new Locale(code1, code2);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
    }

    //endregion
}

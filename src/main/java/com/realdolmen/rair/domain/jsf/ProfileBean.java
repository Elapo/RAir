package com.realdolmen.rair.domain.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "profile")
@RequestScoped
public class ProfileBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables -

    @Inject
    private SessionBean sessionBean;

    //endregion	 

    //region Private Properties -

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors +

    public ProfileBean() {}

    //endregion

    //region Public Properties -

    //endregion

    //region Public Methods - 

    //endregion

}

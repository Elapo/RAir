package com.realdolmen.rair.domain.jsf;


import com.realdolmen.rair.domain.Authorizer;
import com.realdolmen.rair.domain.entities.user.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "userSession")
@SessionScoped
public class SessionBean implements Serializable{

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    @Inject
    private Authorizer authorizer;

    //endregion	 

    //region Private Properties - 

    //endregion

    //region Private Methods -

    //endregion	

    //region Constructors - 

    //endregion

    //region Public Properties +

    public Authorizer getAuthorizer() {
        return authorizer;
    }

    public boolean isLoggedIn() {
        return authorizer != null && authorizer.getUser() != null;
    }

    //endregion

    //region Public Methods - 

    //endregion

}

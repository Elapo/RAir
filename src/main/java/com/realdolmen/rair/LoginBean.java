package com.realdolmen.rair;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "login")
@RequestScoped
public class LoginBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    private String password;
    private String email;

    //endregion	 

    //region Private Properties - 

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors - 

    //endregion

    //region Public Properties +

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion

    //region Public Methods - 

    //endregion

}

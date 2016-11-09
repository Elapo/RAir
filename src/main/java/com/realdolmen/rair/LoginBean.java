package com.realdolmen.rair;

import com.realdolmen.rair.data.PasswordManager;
import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "login")
@RequestScoped
public class LoginBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables +

    @Inject
    private PasswordManager passwordManager;

    @Inject
    private UserDao userDao;

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

    public void logUserIn() {
        User user = userDao.find(email);
        byte[] bytes = passwordManager.hashText(user.getSalt(), password);
        if (new String(bytes).equals(user.getHash())) {

        }
    }

    //endregion

}

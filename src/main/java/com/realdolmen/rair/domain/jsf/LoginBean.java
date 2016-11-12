package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.PasswordManager;
import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.controllers.UserController;
import com.realdolmen.rair.domain.entities.user.RegularUser;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

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

    @Inject
    private UserController userController;


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
        User user;
        try {
            user = userDao.find(email);
        } catch (NoResultException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "User or password not found."));
            return;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention!", "User or password not found."));
            return;
        }

        byte[] bytes = passwordManager.hashText(user.getSalt(), password);
        if ( new String(bytes).equals(user.getHash()) ) {
            userController.loginUser(user);
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "User or Password is not correct."));
    }

    public void logUserOut() {
        userController.logOutUser();
    }

    //endregion

}

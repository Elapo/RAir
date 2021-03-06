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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Inject
    private SessionBean sessionBean;

    @Size(min = 1, message = "- Password field can't be empty.")
    private String password;

    @Pattern(message = "- E-mail is not a valid e-mail address.", regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")
    private String email;

    private String page;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    //endregion

    //region Public Methods +

    public void setup() {
        if ( page != null ) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "You need to log in first before you can book."));
    }

    public String logUserIn() {
        if ( !sessionBean.isLoggedIn() ) {
            User user;
            try {
                user = userDao.find(email);
            } catch (NoResultException e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "User or password not found."));
                return null;
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention!", "User or password not found."));
                return null;
            }

            byte[] bytes = passwordManager.hashText(user.getSalt(), password);
            if ( new String(bytes).equals(user.getHash()) ) {
                userController.loginUser(user);
                if ( page != null ) {
                    switch( page.toLowerCase().trim() ) {
                        case "payment": {
                            page = null;
                            return "pretty:payment";
                        }
                    }
                }
                return "pretty:index";
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "User or Password is not correct."));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention!", "You're already logged in.."));
        return null;
    }

    public void loginAsPartner() {
        email = "mail@jetair.be";
        password = "test";
        logUserIn();
    }

    public void loginAsCu() {
        email = "worker@realdolmen.com";
        password = "test";
        logUserIn();
    }

    public void loginAsRu() {
        email = "john.doe@gmail.com";
        password = "test";
        logUserIn();
    }

    public String logUserOut() {
        userController.logOutUser();
        return "index?faces-redirect=true";
    }

    public String logAdminUserOut() {
        userController.logOutUser();
        return "index";
    }

    //endregion

}

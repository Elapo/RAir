package com.realdolmen.rair.domain.controllers;
import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.entities.user.User;
import com.realdolmen.rair.domain.jsf.SessionBean;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserController extends AbstractController {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables - 

    @Inject
    private UserDao userDao;

    @Inject
    private SessionBean session;

    //endregion	 

    //region Private Properties - 

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors - 

    //endregion

    //region Public Properties - 

    //endregion

    //region Public Methods +

    public void register(User user) {
        userDao.insert(user);
    }

    public void loginUser(User user) {
        session.getAuthorizer().setUser(user);
    }

    public void logOutUser() {
        session.getAuthorizer().setUser(null);
    }

    //endregion

}

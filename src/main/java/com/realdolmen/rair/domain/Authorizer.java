package com.realdolmen.rair.domain;

import com.realdolmen.rair.domain.entities.user.*;

import javax.inject.Named;
import java.io.Serializable;

@Named
public class Authorizer implements Serializable {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public AuthLevel check(Class<? extends Authorizable>... allowedTypes) {
        if (user == null) {
            return AuthLevel.GUEST;
        }

        for (Class<? extends Authorizable> t : allowedTypes) {
            if (t != null && t.isAssignableFrom(user.getClass())) {
                return AuthLevel.ALLOWED;
            }
        }

        return AuthLevel.NOT_ALLOWED;
    }

    public boolean check(AuthLevel requiredLevel, Class<? extends Authorizable>... allowedTypes) {
        return check(allowedTypes) == requiredLevel;

    }

    public boolean check(AuthLevel requiredLevel, String... allowedTypes) {
        Class[] classes = new Class[allowedTypes.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = asClass("com.realdolmen.rair.domain.entities.user." + allowedTypes[i]);
        }
        return check(classes) == requiredLevel;

    }

    public Class<? extends Authorizable> asClass(String clazz) {
        try {
            Class<?> c = this.getClass().getClassLoader().loadClass(clazz);
            return ((Authorizable) c.newInstance()).getClass();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkRegularUser(AuthLevel level) {
        return check(level, RegularUser.class);
    }

    public boolean checkPartner(AuthLevel level) {
        return check(level, Partner.class);
    }

    public boolean checkUser(AuthLevel level) {
        return check(level, User.class);
    }

    public boolean checkCompanyUser(AuthLevel level) {
        return check(level, CompanyUser.class);
    }

    public boolean checkAdmin(AuthLevel level) {
        return check(level, Admin.class);
    }
}
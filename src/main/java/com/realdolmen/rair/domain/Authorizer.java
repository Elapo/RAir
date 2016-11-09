package com.realdolmen.rair.domain;

import com.realdolmen.rair.domain.entities.user.User;

public class Authorizer {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public AuthLevel check(Class<? extends Authorizable>... allowedTypes) {
        if(user == null) {
            return AuthLevel.GUEST;
        }

        for(Class<? extends Authorizable> t : allowedTypes) {
            if(t.isAssignableFrom(user.getClass())) {
                return AuthLevel.ALLOWED;
            }
        }

        return AuthLevel.NOT_ALLOWED;
    }

    public boolean check(AuthLevel requiredLevel, Class<? extends Authorizable>... allowedTypes) {
        return check(allowedTypes) == requiredLevel;

    }
}

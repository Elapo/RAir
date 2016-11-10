package com.realdolmen.rair.domain;

import com.realdolmen.rair.domain.entities.user.User;

public class Authorizer {
    private User user;

    public void setUser(User user) {
        this.user = user;
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
            classes[i] = asClass(allowedTypes[i]);
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
}

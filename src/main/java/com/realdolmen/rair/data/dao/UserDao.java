package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDao extends AbstractDao<User, Long> {

    @Override
    protected Class<User> getDataType() {
        return User.class;
    }

    public <T extends User> T find(Class<T> c, Long id) {
        return (T) super.find(id);
    }

    public User find(String email) {
        return  super.em().createNamedQuery("User.FindByEmail", User.class).setParameter("email", email).getSingleResult();
    }
}

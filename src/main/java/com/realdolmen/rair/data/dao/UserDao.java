package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.user.Partner;
import com.realdolmen.rair.domain.entities.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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

    public <T extends User> List<T> findAll(Class<T> c) {
        return findByType(c);
    }

    @SuppressWarnings("all")
    public <T extends User> List<T> findByType(Class<T> c) {
        return (List<T>) em().createNamedQuery("User.findByType", User.class).setParameter("type", c).getResultList();
    }
}

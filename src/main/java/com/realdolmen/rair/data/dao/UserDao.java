package com.realdolmen.rair.data.dao;

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

    public <T extends User> List<T> findAll(Class<T> c) {
        CriteriaQuery<T> cq = (CriteriaQuery<T>) findByCriteria();
        TypedQuery<T> q = em().createQuery(cq);
        return q.getResultList();
    }
}

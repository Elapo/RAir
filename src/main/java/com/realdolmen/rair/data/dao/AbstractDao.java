package com.realdolmen.rair.data.dao;

import org.hibernate.StaleObjectStateException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T, I> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public EntityManager em() {
        return em;
    }

    public T find(I id) {
        return em.find(getDataType(), id);
    }

    public List<T> findAll() {
        CriteriaQuery<T> cq = findByCriteria();
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }

    public CriteriaQuery<T> findByCriteria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDataType());
        Root<T> root = cq.from(getDataType());
        return cq.select(root);
    }

    void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected abstract Class<T> getDataType();

    @Transactional
    public void insert(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null!");
        }

        if (!validate(false, object)) {
            throw new IllegalStateException("Object is invalid!");
        }

        if (em.contains(object)) {
            update(object);
        } else {
            em.persist(object);
        }
    }

    @Transactional
    public T update(T object) throws StaleObjectStateException {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null!");
        }

        if (!validate(true, object)) {
            throw new IllegalStateException("Object is invalid!");
        }
        return em.merge(object);
    }

    @Transactional
    public void remove(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null!");
        }

        em.remove(object);
    }

    protected boolean validate(boolean update, T object) {
        return true;
    }

}

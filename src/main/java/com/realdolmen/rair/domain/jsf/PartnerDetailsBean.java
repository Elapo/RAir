package com.realdolmen.rair.domain.jsf;

import com.realdolmen.rair.data.dao.UserDao;
import com.realdolmen.rair.domain.entities.user.Partner;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PartnerDetailsBean {

    private Long partnerId;

    private Partner partner;

    @Inject
    private UserDao userDao;

    public void load() {
        partner = userDao.find(Partner.class, partnerId);
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Long getPartnerId() {
        return partnerId;
    }
}

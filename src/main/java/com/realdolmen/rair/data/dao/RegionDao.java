package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Region;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegionDao extends AbstractDao<Region, Long> {

    @Override
    protected Class<Region> getDataType() {
        return Region.class;
    }

    @Override
    protected boolean validate(boolean update, Region object) {
        if(!update && object.getId() != null) {
            return false;
        }

        return true;
    }
}

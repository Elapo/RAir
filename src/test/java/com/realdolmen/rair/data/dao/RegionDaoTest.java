package com.realdolmen.rair.data.dao;

import com.realdolmen.rair.domain.entities.Region;
import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RegionDaoTest extends JpaPersistenceTest {

    private RegionDao rd;

    @Before
    public void setUp() throws Exception {
        rd = new RegionDao();
        rd.setEntityManager(entityManager());
    }

    @Test
    public void findReturnsRegion() throws Exception {
        Region region = rd.find(1000L);
        assertNotNull(region);
    }

    @Test
    public void findAllReturnsListOfRegions() throws Exception {
        List<Region> regionList = rd.findAll();
        assertNotNull(regionList);
        assertFalse(regionList.isEmpty());
    }

    @Test
    public void insert() throws Exception {
        Region region = new Region();
        region.setName("TEST LAND");
        rd.insert(region);
        Region dbRegion = entityManager().find(Region.class, region.getId());
        assertEquals(region, dbRegion);
    }

    @Test
    public void update() throws Exception {
        Region er = entityManager().find(Region.class, 1000L);
        er.setName("New name");
        rd.update(er);
        Region ur = entityManager().find(Region.class, 1000L);
        assertEquals(er.getName(), ur.getName());
    }

    @Test
    public void remove() throws Exception {
        Region r = entityManager().getReference(Region.class, 1000L);
        rd.remove(r);
        assertNull(entityManager().getReference(Region.class, 1000L));
    }
}
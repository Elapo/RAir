package com.realdolmen.rair.domain.entities.user;

import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import org.junit.Test;

public class UserTest extends JpaPersistenceTest {

    @Test
    public void canGetRegularUser() throws Exception {
        RegularUser user = entityManager().find(RegularUser.class, 1000L);

        assertNotNull(user);
        assertEquals(1000L, (long) user.getId());
        assertNotNull(user.getAddress());
    }

    @Test
    public void canGetCompanyUser() throws Exception {
        CompanyUser user = entityManager().find(CompanyUser.class, 3000L);

        assertNotNull(user);
        assertEquals(3000L, (long) user.getId());
        assertNotNull(user.getAddress());
    }

    @Test
    public void canGetAdminUser() throws Exception {
        Admin user = entityManager().find(Admin.class, 4000L);

        assertNotNull(user);
        assertEquals(4000L, (long) user.getId());
        assertNotNull(user.getAddress());
    }

    @Test
    public void canGetPartnerUser() throws Exception {
        Partner user = entityManager().find(Partner.class, 2000L);

        assertNotNull(user);
        assertEquals(2000L, (long) user.getId());
        assertNotNull(user.getAddress());
    }

    @Test
    public void canCreateRegularUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        RegularUser ru = new RegularUser("John", "Doe", "john.doe@realdolmen.com", "+32(0)12 34 56 78", "JohnRocks64", address);
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        RegularUser dbRu = entityManager().find(RegularUser.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreateCompanyUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        CompanyUser ru = new CompanyUser("John", "Doe", "john.doe@realdolmen.com", "+32(0)12 34 56 78", "JohnRocks64", address);
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        CompanyUser dbRu = entityManager().find(CompanyUser.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreateAdminUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        Admin ru = new Admin("John", "Doe", "john.doe@realdolmen.com", "+32(0)12 34 56 78", "JohnRocks64", address);
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        Admin dbRu = entityManager().find(Admin.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreatePartnerUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        Partner ru = new Partner("John", "Doe", "john.doe@realdolmen.com", "+32(0)12 34 56 78", "JohnRocks64", address);
        Flight flight = new Flight();
        ru.getOwnedFlights().add(flight);
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        Partner dbRu = entityManager().find(Partner.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(flight, ru.getOwnedFlights().get(0));
        assertEquals(ru, dbRu);
    }
}
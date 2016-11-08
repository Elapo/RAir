package com.realdolmen.rair.domain.entities.user;

import com.realdolmen.rair.domain.entities.Address;
import com.realdolmen.rair.domain.entities.Flight;
import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import com.realdolmen.rair.util.persistence.NoTransaction;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import java.util.Date;

public class UserTest extends JpaPersistenceTest {

    @Test
    public void canGetRegularUser() throws Exception {
        RegularUser user = entityManager().find(RegularUser.class, 1000L);

        assertNotNull(user);
        assertEquals(1000L, (long) user.getId());
        assertNotNull(user.getContactInformation().getAddress());
    }

    @Test
    public void canGetCompanyUser() throws Exception {
        CompanyUser user = entityManager().find(CompanyUser.class, 3000L);

        assertNotNull(user);
        assertEquals(3000L, (long) user.getId());
        assertNotNull(user.getContactInformation().getAddress());
    }

    @Test
    public void canGetAdminUser() throws Exception {
        Admin user = entityManager().find(Admin.class, 4000L);

        assertNotNull(user);
        assertEquals(4000L, (long) user.getId());
        assertNotNull(user.getContactInformation().getAddress());
    }

    @Test
    public void canGetPartnerUser() throws Exception {
        Partner user = entityManager().find(Partner.class, 2000L);

        assertNotNull(user);
        assertEquals(2000L, (long) user.getId());
        assertNotNull(user.getContactInformation().getAddress());
    }

    @Test
    public void canCreateRegularUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        RegularUser ru = new RegularUser("John", "Doe", "JohnRocks64",
                new ContactInformation("john.doe@realdolmen.com", "+32(0)12 34 56 78", address));
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        RegularUser dbRu = entityManager().find(RegularUser.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreateCompanyUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        CompanyUser ru = new CompanyUser("John", "Doe", "JohnRocks64",
                new ContactInformation("john.doe@realdolmen.com", "+32(0)12 34 56 78", address));
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        CompanyUser dbRu = entityManager().find(CompanyUser.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreateAdminUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        Admin ru = new Admin("John", "Doe", "JohnRocks64",
                new ContactInformation("john.doe@realdolmen.com", "+32(0)12 34 56 78", address));
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        Admin dbRu = entityManager().find(Admin.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(ru, dbRu);
    }

    @Test
    public void canCreatePartnerUser() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        Partner ru = new Partner("John", "Doe", "JohnRocks64",
                new ContactInformation("john.doe@realdolmen.com", "+32(0)12 34 56 78", address));
        Flight flight = new Flight();
        ru.getOwnedFlights().add(flight);
        entityManager().persist(ru);

        assertNotNull(ru.getId());
        Partner dbRu = entityManager().find(Partner.class, ru.getId());
        assertNotNull(dbRu);
        assertEquals(flight, ru.getOwnedFlights().get(0));
        assertEquals(ru, dbRu);
    }

    @Test
    public void canDeleteUsers() throws Exception {
        entityManager().remove(entityManager().getReference(RegularUser.class, 1000L));
        entityManager().remove(entityManager().getReference(CompanyUser.class, 3000L));
        entityManager().remove(entityManager().getReference(Admin.class, 4000L));
    }

    @Test
    @NoTransaction
    @Ignore
    public void deletePartnerWithFlightsBoundToRouteNotAllowed() {
        //TODO: Catch failure in some way
        try {
            entityManager().remove(entityManager().getReference(Partner.class, 2000L));
        } catch (Exception re) {
            if(re.getCause() instanceof ConstraintViolationException) {

            } else {
                fail("Exception was not ConstraintViolation");
                re.printStackTrace();
            }
        }

        fail("Bound flights should not be able to be deleted!");
    }

    @Test
    public void deletingPartnerRemovesAllOwnedFlights() throws Exception {
        Address address = new Address("Blank street", "15a", "Mystery", "9191", "Christmas Island");
        Partner ru = new Partner("John", "Doe", "JohnRocks64",
                new ContactInformation("john.doe@realdolmen.com", "+32(0)12 34 56 78", address));
        Flight flight = new Flight();
        ru.getOwnedFlights().add(flight);
        entityManager().persist(ru);

        entityManager().remove(ru);
        Flight ghostFlight = entityManager().find(Flight.class, flight.getId());
        assertNull(ghostFlight);
    }

    @Test
    public void updateRegularUser() throws Exception {
        RegularUser ru = entityManager().find(RegularUser.class, 1000L);

        //Update a basic field
        ru.setFirstName("Updated");
        entityManager().merge(ru);
        RegularUser dbUser = entityManager().find(RegularUser.class, ru.getId());
        assertEquals("Updated", dbUser.getFirstName());

        //Update nested field
        ru.getContactInformation().getAddress().setCity("UpdateCity");

        dbUser = entityManager().find(RegularUser.class, ru.getId());
        assertEquals("UpdateCity", dbUser.getContactInformation().getAddress().getCity());
    }

    @Test
    public void updateCompanyUser() throws Exception {
        CompanyUser ru = entityManager().find(CompanyUser.class, 3000L);

        //Update a basic field
        ru.setFirstName("Updated");
        entityManager().merge(ru);
        CompanyUser dbUser = entityManager().find(CompanyUser.class, ru.getId());
        assertEquals("Updated", dbUser.getFirstName());

        //Update nested field
        ru.getContactInformation().getAddress().setCity("UpdateCity");

        dbUser = entityManager().find(CompanyUser.class, ru.getId());
        assertEquals("UpdateCity", dbUser.getContactInformation().getAddress().getCity());
    }

    @Test
    public void updatePartnerUser() throws Exception {
        Partner ru = entityManager().find(Partner.class, 2000L);

        //Update a basic field
        ru.setFirstName("Updated");
        entityManager().merge(ru);
        Partner dbUser = entityManager().find(Partner.class, ru.getId());
        assertEquals("Updated", dbUser.getFirstName());

        //Update nested field
        ru.getContactInformation().getAddress().setCity("UpdateCity");
        Date dt = new Date();
        ru.getOwnedFlights().get(0).setDepartureTime(dt);

        dbUser = entityManager().find(Partner.class, ru.getId());
        assertEquals("UpdateCity", dbUser.getContactInformation().getAddress().getCity());
        assertEquals(dt, dbUser.getOwnedFlights().get(0).getDepartureTime());
    }

    @Test
    public void updateAdminUser() throws Exception {
        Admin ru = entityManager().find(Admin.class, 4000L);

        //Update a basic field
        ru.setFirstName("Updated");
        entityManager().merge(ru);
        Admin dbUser = entityManager().find(Admin.class, ru.getId());
        assertEquals("Updated", dbUser.getFirstName());

        //Update nested field
        ru.getContactInformation().getAddress().setCity("UpdateCity");

        dbUser = entityManager().find(Admin.class, ru.getId());
        assertEquals("UpdateCity", dbUser.getContactInformation().getAddress().getCity());
    }
}
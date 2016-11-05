package com.realdolmen.rair.domain.entities.user;

import com.realdolmen.rair.util.persistence.JpaPersistenceTest;
import org.junit.Test;

public class UserTest extends JpaPersistenceTest {

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
}
package com.realdolmen.rair.domain.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AD")
public class Admin extends User {
}

package com.realdolmen.rair.domain.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RU")
public class RegularUser extends User {

}

package com.realdolmen.rair.domain.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CU")
public class CompanyUser extends User {

}

package com.realdolmen.rair.domain.modifiers;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class PriceModifier {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    public abstract BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input);
}

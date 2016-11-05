package com.realdolmen.rair.domain.modifiers;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PriceModifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public abstract BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input);


    public BigDecimal multiplyPercentage(BigDecimal input, double percentage) {
        return multiplyPercentage(input, percentage, RoundingMode.UNNECESSARY);
    }

    public BigDecimal multiplyPercentage(BigDecimal input, double percentage, RoundingMode roundingMode) {
        return input.multiply(new BigDecimal(percentage)).divide(new BigDecimal(100), roundingMode);
    }
}

package com.realdolmen.rair.domain.modifiers;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CreditCardModifier extends PriceModifier {

    private double creditCardDiscountPercentage = 5.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input) {
        return input.min(multiplyPercentage(input, creditCardDiscountPercentage));
    }
}

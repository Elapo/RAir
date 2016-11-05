package com.realdolmen.rair.domain.modifiers;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class MarginModifier extends PriceModifier {

    private double marginPercentage;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input) {
        if (marginPercentage == 0) {
            return input;
        }
        return input.add(multiplyPercentage(input, marginPercentage));
    }
}

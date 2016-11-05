package com.realdolmen.rair.domain.modifiers;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
//@DiscriminatorValue("Margin")
public class MarginModifier extends PriceModifier {

    private double marginPercentage;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input) {
        if (marginPercentage == 0) {
            return input;
        }
        return input.multiply(new BigDecimal(marginPercentage)).divide(new BigDecimal(100), BigDecimal.ROUND_UNNECESSARY);
    }
}

package com.realdolmen.rair.domain.modifiers;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class MarginModifier extends PriceModifier {

    private Double margin = 10.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input) {
        if (margin == 0) {
            return input;
        }

        if (isPercentBased())
            return input.add(multiplyPercentage(input, margin));
        else
            return input.add(new BigDecimal(margin));
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }
}

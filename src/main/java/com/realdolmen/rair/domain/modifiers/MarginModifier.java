package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("Margin")
public class MarginModifier extends PriceModifier {

    private Double margin = 10.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, Flight flight, Booking booking, BigDecimal input) {
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

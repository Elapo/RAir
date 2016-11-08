package com.realdolmen.rair.domain.modifiers;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CreditCardModifier extends PriceModifier {

    private Double discount = 5.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, BigDecimal input) {
        if (isPercentBased())
            return input.min(multiplyPercentage(input, discount));
        else
            return input.min(new BigDecimal(discount));
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double creditCardDiscountPercentage) {
        this.discount = creditCardDiscountPercentage;
    }
}

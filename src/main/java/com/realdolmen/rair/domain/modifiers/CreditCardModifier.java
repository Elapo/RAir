package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("CreditCard")
public class CreditCardModifier extends PriceModifier {

    private Double discount = 5.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, Flight flight, Booking booking, BigDecimal input) {
        if (isPercentBased())
            return input.subtract(multiplyPercentage(input, discount));
        else
            return input.subtract(new BigDecimal(discount));
    }

    @Override
    public String getPrettyName() {
        return "Credit Card Discount";
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double creditCardDiscountPercentage) {
        this.discount = creditCardDiscountPercentage;
    }
}

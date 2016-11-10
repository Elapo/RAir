package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import java.math.BigDecimal;
import java.util.List;

public class VolumeDiscountModifier extends PriceModifier {

    private int numberOfTickets = 5;
    private double discount = 5.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, Flight flight, Booking booking, BigDecimal input) {
        if(isPercentBased()) {
            if(booking.getTickets().size() >= numberOfTickets) {
                return input.subtract(multiplyPercentage(input, discount));
            }
        } else {
            if(booking.getTickets().size() >= numberOfTickets) {
                return input.subtract(new BigDecimal(discount));
            }
        }

        return input;
    }

    @Override
    public String getPrettyName() {
        return "Volume Discount";
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

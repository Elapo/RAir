package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("VolumeDiscount")
public class VolumeDiscountModifier extends PriceModifier implements ConditionalModifier {

    private int numberOfTickets = 5;
    private double discount = 5.0;

    @Override
    public BigDecimal modify(List<PriceModifier> modifiers, Flight flight, Booking booking, BigDecimal input) {
        if (isPercentBased()) {
            if (booking.getTickets().size() >= numberOfTickets) {
                return input.subtract(multiplyPercentage(input, discount));
            }
        } else {
            if (booking.getTickets().size() >= numberOfTickets) {
                return input.subtract(new BigDecimal(discount));
            }
        }

        return input;
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

    @Override
    public boolean include(Booking booking) {
        return booking.getTickets() != null && booking.getTickets().size() >= numberOfTickets && isHighestApplicableDiscount(booking);
    }

    private boolean isHighestApplicableDiscount(Booking booking) {

        VolumeDiscountModifier highestFound = this;

        for (PriceModifier modifier : booking.getPriceModifiers()) {
            if (modifier instanceof VolumeDiscountModifier) {
                VolumeDiscountModifier other = (VolumeDiscountModifier) modifier;

//                int nrOfTickets = booking.getTickets().size();
                //TODO: test

                if (other.getDiscount() > highestFound.getDiscount() && other.getNumberOfTickets() >= highestFound.getNumberOfTickets()) {
                    highestFound = other;
                }
            }
        }

        return highestFound == this;
    }
}

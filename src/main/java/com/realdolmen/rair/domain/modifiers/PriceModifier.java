package com.realdolmen.rair.domain.modifiers;


import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.Flight;

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

    @Column(name = "orderIndex")
    private Integer index;

    private Boolean percentBased = true;

    public abstract BigDecimal modify(List<PriceModifier> modifiers, Flight flight, Booking booking, BigDecimal input);


    public BigDecimal multiplyPercentage(BigDecimal input, double percentage) {
        return multiplyPercentage(input, percentage, RoundingMode.HALF_UP);
    }

    public BigDecimal multiplyPercentage(BigDecimal input, double percentage, RoundingMode roundingMode) {
        return input.multiply(new BigDecimal(percentage)).setScale(2, roundingMode).divide(new BigDecimal(100), roundingMode);
    }

    public boolean isPercentBased() {
        return percentBased;
    }

    public Boolean getPercentBased() {
        return percentBased;
    }

    public void setPercentBased(boolean percentBased) {
        this.percentBased = percentBased;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}

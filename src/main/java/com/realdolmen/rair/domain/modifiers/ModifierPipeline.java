package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModifierPipeline {
    private List<PriceModifier> modifiers = new ArrayList<>();

    private ModifierPipeline() {

    }

    public ModifierPipeline addLast(PriceModifier modifier) {
        int index = modifiers.size() - 1;
        modifier.setIndex(index);
        modifiers.add(modifier);
        return this;
    }

    public static ModifierPipeline loadIntoOrder(List<PriceModifier> incoming) {
        ModifierPipeline pipeline = new ModifierPipeline();
        for (int i = 0; i < incoming.size(); i++) {
            incoming.get(i).setIndex(i);
        }

        pipeline.modifiers = incoming.stream().sorted((o1, o2) -> o1.getIndex() - o2.getIndex()).collect(Collectors.toList());
        return pipeline;
    }

    public BigDecimal pass(BigDecimal input, Booking booking) {
        if (input == null)
            return null;
        BigDecimal result = input;

        for (PriceModifier modifier : modifiers) {
            if (modifier instanceof ConditionalModifier) {
                ConditionalModifier m = (ConditionalModifier) modifier;
                if (m.include(booking)) {
                    result = modifier.modify(modifiers, booking.getFlight(), booking, result);
                }
            } else {
                result = modifier.modify(modifiers, booking.getFlight(), booking, result);
            }
        }

        return result;
    }

    public List<PriceModifier> getOrderedModifiers() {
        return modifiers;
    }
}

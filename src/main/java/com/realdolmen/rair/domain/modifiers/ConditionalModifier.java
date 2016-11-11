package com.realdolmen.rair.domain.modifiers;

import com.realdolmen.rair.domain.entities.Booking;

public interface ConditionalModifier {
    boolean include(Booking booking);
}

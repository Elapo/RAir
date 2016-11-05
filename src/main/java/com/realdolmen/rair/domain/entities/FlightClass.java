package com.realdolmen.rair.domain.entities;

public enum FlightClass {
    FIRST_CLASS("First Class"), BUSINESS_CLASS("Business Class"), PREMIUM_ECONOMY("Premium Economy"), ECONOMY_CLASS("Economy Class");

    private String prettyName;

    FlightClass(String pretty) {
        prettyName = pretty;
    }

    @Override
    public String toString() {
        return prettyName;
    }
}

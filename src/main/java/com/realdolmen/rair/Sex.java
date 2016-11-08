package com.realdolmen.rair;

public enum Sex {

    MALE("Male"), FEMALE("Female");

    private String prettyName;

    Sex(String pretty) {
        prettyName = pretty;
    }

    @Override
    public String toString() {
        return prettyName;
    }

}

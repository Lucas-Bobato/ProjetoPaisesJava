package com.countries.model;

public enum Continent {
    AFRICA("África"),
    ASIA("Ásia"),
    EUROPE("Europa"),
    NORTH_AMERICA("América do Norte"),
    SOUTH_AMERICA("América do Sul"),
    OCEANIA("Oceania"),
    ANTARCTICA("Antártida");
    
    private final String displayName;
    
    Continent(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
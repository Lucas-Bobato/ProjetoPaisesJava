package com.countries.model;

public class Country {
    private String name;
    private String capital;
    private Continent continent;
    private String flagUrl;
    
    // Construtor padrão
    public Country() {
    }
    
    // Construtor com todos os campos
    public Country(String name, String capital, Continent continent, String flagUrl) {
        this.name = name;
        this.capital = capital;
        this.continent = continent;
        this.flagUrl = flagUrl;
    }
    
    // Getters e Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public void setCapital(String capital) {
        this.capital = capital;
    }
    
    public Continent getContinent() {
        return continent;
    }
    
    public void setContinent(Continent continent) {
        this.continent = continent;
    }
    
    public String getFlagUrl() {
        return flagUrl;
    }
    
    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
    
    // Método auxiliar para obter o nome do continente para exibição
    public String getContinentDisplayName() {
        return continent.getDisplayName();
    }
}
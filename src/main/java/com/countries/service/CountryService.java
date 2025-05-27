package com.countries.service;

import com.countries.model.Continent;
import com.countries.model.Country;
import java.util.List;
import java.util.Optional; // Import Optional

public interface CountryService {
    List<Country> getAllCountries();
    List<Country> getCountriesByContinent(Continent continent);
    List<Country> getSortedCountries(String sortBy);
    List<Country> getSortedCountriesByContinent(Continent continent, String sortBy);
    List<Continent> getAllContinents();

    // New methods for CRUD operations
    void addCountry(Country country);
    Optional<Country> getCountryByName(String name); // Changed to Optional
    void updateCountry(String originalName, Country country);
    void deleteCountry(String name);
}
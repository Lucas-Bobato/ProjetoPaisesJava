package com.countries.service;

import com.countries.model.Continent;
import com.countries.model.Country;
import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
    List<Country> getCountriesByContinent(Continent continent);
    List<Country> getSortedCountries(String sortBy);
    List<Country> getSortedCountriesByContinent(Continent continent, String sortBy);
    List<Continent> getAllContinents();
}
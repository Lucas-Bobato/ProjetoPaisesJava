package com.countries.service;

import com.countries.model.Continent;
import com.countries.model.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional; // Import Optional
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final List<Country> countries;

    public CountryServiceImpl() {
        this.countries = new ArrayList<>();
        initializeCountries();
    }

    private void initializeCountries() {
        // América do Sul
        countries.add(new Country("Brasil", "Brasília", Continent.SOUTH_AMERICA, "https://flagcdn.com/w320/br.png"));
        countries.add(new Country("Argentina", "Buenos Aires", Continent.SOUTH_AMERICA, "https://flagcdn.com/w320/ar.png"));
        countries.add(new Country("Chile", "Santiago", Continent.SOUTH_AMERICA, "https://flagcdn.com/w320/cl.png"));
        countries.add(new Country("Colômbia", "Bogotá", Continent.SOUTH_AMERICA, "https://flagcdn.com/w320/co.png"));
        countries.add(new Country("Peru", "Lima", Continent.SOUTH_AMERICA, "https://flagcdn.com/w320/pe.png"));

        // Europa
        countries.add(new Country("Alemanha", "Berlim", Continent.EUROPE, "https://flagcdn.com/w320/de.png"));
        countries.add(new Country("França", "Paris", Continent.EUROPE, "https://flagcdn.com/w320/fr.png"));
        countries.add(new Country("Itália", "Roma", Continent.EUROPE, "https://flagcdn.com/w320/it.png"));
        countries.add(new Country("Espanha", "Madrid", Continent.EUROPE, "https://flagcdn.com/w320/es.png"));
        countries.add(new Country("Portugal", "Lisboa", Continent.EUROPE, "https://flagcdn.com/w320/pt.png"));

        // Ásia
        countries.add(new Country("Japão", "Tóquio", Continent.ASIA, "https://flagcdn.com/w320/jp.png"));
        countries.add(new Country("China", "Pequim", Continent.ASIA, "https://flagcdn.com/w320/cn.png"));
        countries.add(new Country("Índia", "Nova Delhi", Continent.ASIA, "https://flagcdn.com/w320/in.png"));
        countries.add(new Country("Coreia do Sul", "Seul", Continent.ASIA, "https://flagcdn.com/w320/kr.png"));
        countries.add(new Country("Tailândia", "Bangkok", Continent.ASIA, "https://flagcdn.com/w320/th.png"));

        // América do Norte
        countries.add(new Country("Estados Unidos", "Washington D.C.", Continent.NORTH_AMERICA, "https://flagcdn.com/w320/us.png"));
        countries.add(new Country("Canadá", "Ottawa", Continent.NORTH_AMERICA, "https://flagcdn.com/w320/ca.png"));
        countries.add(new Country("México", "Cidade do México", Continent.NORTH_AMERICA, "https://flagcdn.com/w320/mx.png"));
        countries.add(new Country("Costa Rica", "San José", Continent.NORTH_AMERICA, "https://flagcdn.com/w320/cr.png"));
        countries.add(new Country("Panamá", "Cidade do Panamá", Continent.NORTH_AMERICA, "https://flagcdn.com/w320/pa.png"));

        // África
        countries.add(new Country("África do Sul", "Pretória", Continent.AFRICA, "https://flagcdn.com/w320/za.png"));
        countries.add(new Country("Egito", "Cairo", Continent.AFRICA, "https://flagcdn.com/w320/eg.png"));
        countries.add(new Country("Nigéria", "Abuja", Continent.AFRICA, "https://flagcdn.com/w320/ng.png"));
        countries.add(new Country("Quênia", "Nairóbi", Continent.AFRICA, "https://flagcdn.com/w320/ke.png"));
        countries.add(new Country("Marrocos", "Rabat", Continent.AFRICA, "https://flagcdn.com/w320/ma.png"));

        // Oceania
        countries.add(new Country("Austrália", "Camberra", Continent.OCEANIA, "https://flagcdn.com/w320/au.png"));
        countries.add(new Country("Nova Zelândia", "Wellington", Continent.OCEANIA, "https://flagcdn.com/w320/nz.png"));
        countries.add(new Country("Fiji", "Suva", Continent.OCEANIA, "https://flagcdn.com/w320/fj.png"));
    }

    @Override
    public List<Country> getAllCountries() {
        return new ArrayList<>(countries);
    }

    @Override
    public List<Country> getCountriesByContinent(Continent continent) {
        return countries.stream()
                .filter(country -> country.getContinent() == continent)
                .collect(Collectors.toList());
    }

    @Override
    public List<Country> getSortedCountries(String sortBy) {
        List<Country> sortedList = new ArrayList<>(countries);
        sortByField(sortedList, sortBy);
        return sortedList;
    }

    @Override
    public List<Country> getSortedCountriesByContinent(Continent continent, String sortBy) {
        List<Country> filteredList = getCountriesByContinent(continent);
        sortByField(filteredList, sortBy);
        return filteredList;
    }

    @Override
    public List<Continent> getAllContinents() {
        return Arrays.asList(Continent.values());
    }

    private void sortByField(List<Country> countryList, String sortBy) { // Renamed parameter to avoid conflict
        if (sortBy == null) {
            return;
        }
        switch (sortBy.toLowerCase()) {
            case "name":
                countryList.sort(Comparator.comparing(Country::getName));
                break;
            case "capital":
                countryList.sort(Comparator.comparing(Country::getCapital));
                break;
            case "continent":
                countryList.sort(Comparator.comparing(Country::getContinentDisplayName));
                break;
            default:
                countryList.sort(Comparator.comparing(Country::getName));
                break;
        }
    }

    // --- New CRUD Method Implementations ---

    @Override
    public void addCountry(Country country) {
        // Basic check for duplicates by name
        if (countries.stream().noneMatch(c -> c.getName().equalsIgnoreCase(country.getName()))) {
            countries.add(country);
        } else {
            // Handle duplicate name scenario, e.g., throw an exception or log a warning
            System.err.println("Country with name " + country.getName() + " already exists.");
        }
    }

    @Override
    public Optional<Country> getCountryByName(String name) {
        return countries.stream()
                .filter(country -> country.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public void updateCountry(String originalName, Country updatedCountry) {
        Optional<Country> existingCountryOpt = getCountryByName(originalName);
        if (existingCountryOpt.isPresent()) {
            // If the name is also being updated, we need to ensure the new name isn't a duplicate
            // (unless it's the same as the original name)
            if (!originalName.equalsIgnoreCase(updatedCountry.getName()) && 
                getCountryByName(updatedCountry.getName()).isPresent()) {
                System.err.println("Cannot update to name " + updatedCountry.getName() + " as it already exists.");
                return; // Or throw an exception
            }
            
            // Remove the old entry and add the updated one
            // This is simpler for in-memory list, especially if the "key" (name) can change
            countries.removeIf(c -> c.getName().equalsIgnoreCase(originalName));
            countries.add(updatedCountry);
        } else {
            System.err.println("Country with name " + originalName + " not found for update.");
        }
    }

    @Override
    public void deleteCountry(String name) {
        boolean removed = countries.removeIf(country -> country.getName().equalsIgnoreCase(name));
        if (!removed) {
            System.err.println("Country with name " + name + " not found for deletion.");
        }
    }
}
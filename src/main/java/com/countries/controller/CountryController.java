package com.countries.controller;

import com.countries.model.Continent;
import com.countries.model.Country;
import com.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/countries";
    }

    @GetMapping("/countries")
    public String listCountries(
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String sortBy,
            Model model) {
        
        List<Country> countries;
        
        if (continent != null && !continent.isEmpty()) {
            try {
                Continent selectedContinent = Continent.valueOf(continent);
                if (sortBy != null && !sortBy.isEmpty()) {
                    countries = countryService.getSortedCountriesByContinent(selectedContinent, sortBy);
                } else {
                    countries = countryService.getCountriesByContinent(selectedContinent);
                }
                model.addAttribute("selectedContinent", selectedContinent);
            } catch (IllegalArgumentException e) {
                // Caso de um continente inválido, mostrar todos os países
                countries = sortBy != null && !sortBy.isEmpty()
                        ? countryService.getSortedCountries(sortBy)
                        : countryService.getAllCountries();
            }
        } else {
            countries = sortBy != null && !sortBy.isEmpty()
                    ? countryService.getSortedCountries(sortBy)
                    : countryService.getAllCountries();
        }
        
        model.addAttribute("countries", countries);
        model.addAttribute("continents", countryService.getAllContinents());
        model.addAttribute("sortBy", sortBy);
        
        return "countries";
    }
}
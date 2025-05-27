package com.countries.controller;

import com.countries.model.Continent;
import com.countries.model.Country;
import com.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
                Continent selectedContinent = Continent.valueOf(continent.toUpperCase()); // Ensure uppercase for enum matching
                if (sortBy != null && !sortBy.isEmpty()) {
                    countries = countryService.getSortedCountriesByContinent(selectedContinent, sortBy);
                } else {
                    countries = countryService.getCountriesByContinent(selectedContinent);
                }
                model.addAttribute("selectedContinent", selectedContinent);
            } catch (IllegalArgumentException e) {
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
        
        return "countries"; //
    }

    // --- CRUD Operation Mappings ---

    @GetMapping("/countries/new")
    public String showAddCountryForm(Model model) {
        model.addAttribute("country", new Country()); //
        model.addAttribute("continents", countryService.getAllContinents());
        model.addAttribute("pageTitle", "Adicionar Novo País");
        model.addAttribute("formAction", "/countries/save");
        return "country-form"; // New HTML template for the form
    }

    @GetMapping("/countries/edit/{name}")
    public String showEditCountryForm(@PathVariable("name") String name, Model model) {
        Optional<Country> countryOpt = countryService.getCountryByName(name);
        if (countryOpt.isPresent()) {
            model.addAttribute("country", countryOpt.get());
            model.addAttribute("continents", countryService.getAllContinents());
            model.addAttribute("pageTitle", "Editar País");
            model.addAttribute("originalName", name); // To help identify the country being edited
            model.addAttribute("formAction", "/countries/save");
            return "country-form";
        }
        return "redirect:/countries"; // Or an error page
    }

    @PostMapping("/countries/save")
    public String saveCountry(@ModelAttribute Country country, @RequestParam(value = "originalName", required = false) String originalName) {
        if (originalName != null && !originalName.isEmpty() && !originalName.equals(country.getName())) {
            // This is an update where the name might have changed or other fields changed
            countryService.updateCountry(originalName, country);
        } else if (originalName != null && !originalName.isEmpty() && originalName.equals(country.getName())) {
             // This is an update where the name has not changed, but other fields might have
            countryService.updateCountry(originalName, country);
        }
        else {
            // This is a new country
            countryService.addCountry(country);
        }
        return "redirect:/countries";
    }
    
    @GetMapping("/countries/delete/{name}")
    public String deleteCountry(@PathVariable("name") String name) {
        countryService.deleteCountry(name);
        return "redirect:/countries";
    }
}
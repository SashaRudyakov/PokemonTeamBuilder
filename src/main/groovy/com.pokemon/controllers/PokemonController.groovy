package com.pokemon.controllers;

import com.pokemon.db.tables.pojos.PokemonP
import com.pokemon.db.tables.pojos.Type
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.PokemonService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class PokemonController {

    @InitBinder     /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired PokemonService PokemonService;

    // Show all people in the "people" database
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView pokemon() {
        ModelAndView mav = new ModelAndView("pokemonview");
        mav.addObject("pokemon", PokemonService.getAllPokemon());
        mav.addObject("team", false);
        return mav;
    }

    // shows the form for editing a person
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("pokemonDetailsView");
        PokemonP pokemon = PokemonService.getPokemon(id);
        mav.addObject("pokemon", pokemon)
        return mav;
    }

    // performs saving person logic HIDDEN
    @RequestMapping(value="/save-pokemon", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(value="pokemon") @Valid PokemonP pokemon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            new ModelAndView("pokemonDetailsView")
        }
        else {
            PokemonService.updatePokemon(pokemon);
            new ModelAndView("redirect:/")
        }
    }
}

package com.pokemon.controllers;

import com.pokemon.model.Person;
import com.pokemon.db.tables.pojos.PokemonP
import com.pokemon.db.tables.pojos.Type
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.PersonService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class PersonController {

    @InitBinder     /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired PersonService personService;

    // Show all people in the "people" database
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView people() {
        ModelAndView mav = new ModelAndView("peopleview");
        mav.addObject("people", personService.getPeople());
        return mav;
    }

    // Show all people in the "people" database
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ModelAndView types() {
        ModelAndView mav = new ModelAndView("typesView");
        mav.addObject("types", personService.getTypes());
        return mav;
    }

    // shows the form for editing a person
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView editStrategyPage(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("personview");
        PokemonP person = personService.getPerson(id);
        mav.addObject("person", person)
        return mav;
    }

    // performs saving person logic HIDDEN
    @RequestMapping(value="/save-person", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(value="person") @Valid PokemonP pokemon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            new ModelAndView("personview")
        }
        else {
            personService.updatePerson(pokemon);
            new ModelAndView("redirect:/")
        }
    }


    // Show user the "create a person" form
    @RequestMapping(value = "/create", method=RequestMethod.GET)
    public ModelAndView create() {
        PokemonP person = new PokemonP();
        ModelAndView mav = new ModelAndView("create");
        mav.addObject("person", person);
        return mav;
    }

    // Perform backend query to create a person HIDDEN
    @RequestMapping(value = "/processCreate", method=RequestMethod.POST)
    public ModelAndView processCreate(@ModelAttribute(value="person") PokemonP person) {
        personService.createPerson(person);
        new ModelAndView("redirect:/")
    }

    // Show user the "create a person" form
    @RequestMapping(value = "/createType", method=RequestMethod.GET)
    public ModelAndView createType() {
        Type type = new Type();
        ModelAndView mav = new ModelAndView("createType");
        mav.addObject("type", type);
        return mav;
    }

    // Perform backend query to create a person HIDDEN
    @RequestMapping(value = "/processCreateType", method=RequestMethod.POST)
    public ModelAndView processCreateType(@ModelAttribute(value="type") Type type) {
        personService.createType(type);
        new ModelAndView("redirect:/")
    }

    // Delete a person HIDDEN
    @RequestMapping(value = "/delete", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value="id", required=true) int id) {
        personService.deletePerson(id);
        new ModelAndView("redirect:/")
    }
}

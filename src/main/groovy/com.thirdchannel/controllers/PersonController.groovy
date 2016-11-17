package com.thirdchannel.controllers;

import com.thirdchannel.model.Person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.thirdchannel.services.PersonService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired PersonService personService;

    // Show all people in the "people" database
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView people() {
        ModelAndView mav = new ModelAndView("peopleview");
        mav.addObject("people", personService.getPeople());
        return mav;
    }

    // shows the form for editing a person
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView editStrategyPage(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("personview");
        Person person = personService.getPerson(id);
        mav.addObject("person", person)
        return mav;
    }

    // performs saving person logic HIDDEN
    @RequestMapping(value="/save-person", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(value="person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            new ModelAndView("personview")
        }
        else {
            personService.updatePerson(person);
            new ModelAndView("redirect:/")
        }
    }

    // Show user the "create a person" form
    @RequestMapping(value = "/create", method=RequestMethod.GET)
    public ModelAndView create() {
        Person person = new Person();
        ModelAndView mav = new ModelAndView("create");
        mav.addObject("person", person);
        return mav;
    }

    // Perform backend query to create a person HIDDEN
    @RequestMapping(value = "/processCreate", method=RequestMethod.POST)
    public ModelAndView processCreate(@ModelAttribute(value="person") Person person) {
        personService.createPerson(person);
        new ModelAndView("redirect:/")
    }

    // Delete a person HIDDEN
    @RequestMapping(value = "/delete", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value="id", required=true) int id) {
        personService.deletePerson(id);
        new ModelAndView("redirect:/")
    }
}

package com.pokemon.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.TrainerService;
import com.pokemon.db.tables.pojos.Trainer;
import com.pokemon.db.tables.pojos.Trains;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class TrainerController {

    @Autowired TrainerService trainerService;

    // Show all types
    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public ModelAndView types() {
        ModelAndView mav = new ModelAndView("trainerview");
        mav.addObject("trainers", trainerService.getTrainers());
        return mav;
    }

    // Show user the "create a trainer" form
    @RequestMapping(value = "/create-trainer", method=RequestMethod.GET)
    public ModelAndView create() {
        Trainer t = new Trainer();
        ModelAndView mav = new ModelAndView("createtrainer");
        mav.addObject("trainer", t);
        return mav;
    }

    // Perform backend query to create a person HIDDEN
    @RequestMapping(value = "/process-create", method=RequestMethod.POST)
    public ModelAndView processCreate(@ModelAttribute(value="trainer") Trainer t) {
        trainerService.createTrainer(t);
        new ModelAndView("redirect:/trainers")
    }

    // Delete a person HIDDEN
    @RequestMapping(value = "/delete-trainer", method=RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value="id", required=true) Integer id) {
        trainerService.deleteTrainer(id);
        new ModelAndView("redirect:/trainers")
    }

    // shows the form for editing a person
    @RequestMapping(value="/edit-trainer", method=RequestMethod.GET)
    public ModelAndView editStrategyPage(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("trainerdetails");
        Trainer trainer = trainerService.getTrainer(id);
        mav.addObject("trainer", trainer)
        return mav;
    }

    // performs saving person logic HIDDEN
    @RequestMapping(value="/save-trainer", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(value="trainer") @Valid Trainer trainer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            new ModelAndView("trainerdetails")
        }
        else {
            trainerService.updateTrainer(trainer);
            new ModelAndView("redirect:/trainers");
        }
    }

    // shows the form for editing a person
    @RequestMapping(value="/manage-team", method=RequestMethod.GET)
    public ModelAndView manageTeam(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("pokemonview");
        def trainer = trainerService.getTrainer(id);
        def pokemon = trainerService.getTeam(id);
        Trains t = new Trains();
        t.setTid(id);
        mav.addObject("team", true);
        mav.addObject("trainer", trainer);
        mav.addObject("pokemon", pokemon);
        mav.addObject("trains", t);
        return mav;
    }

    // shows the form for editing a person
    @RequestMapping(value="/drop", method=RequestMethod.GET)
    public ModelAndView drop(@RequestParam(value="pid", required=true) int pid,
                                   @RequestParam(value="tid", required=true) int tid) {
        trainerService.dropPokemon(pid, tid);
        new ModelAndView("redirect:/manage-team?id=" + tid);
    }

    // shows the form for editing a person
    @RequestMapping(value="/add-pokemon", method=RequestMethod.POST)
    public ModelAndView add(@ModelAttribute(value="trains") Trains trains) {
        trainerService.addPokemon(trains);
        new ModelAndView("redirect:/manage-team?id=" + trains.getTid());
    }
}

package com.pokemon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.TrainerService;
import com.pokemon.services.PokemonService;
import com.pokemon.db.tables.pojos.Trainer;
import com.pokemon.db.tables.pojos.Trains;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

import com.pokemon.validators.TrainerValidator;
import com.pokemon.validators.TrainsValidator;


@Controller
public class TrainerController {

    @Autowired TrainerService trainerService;
    @Autowired PokemonService pokemonService;

    @Autowired
    TrainerValidator trainerValidator;

    @Autowired
    TrainsValidator trainsValidator;

    // Show all trainers
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
    public ModelAndView processCreate(@ModelAttribute(value="trainer") @Valid Trainer t, BindingResult bindingResult) {
        trainerValidator.validate(t, bindingResult);
        if (bindingResult.hasErrors()) {
            new ModelAndView("createtrainer")
        }
        else {
            trainerService.createTrainer(t);
            new ModelAndView("redirect:/trainers")
        }
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
        trainerValidator.validate(trainer, bindingResult);
        if (bindingResult.hasFieldErrors('age')) {
            new ModelAndView("trainerdetails")
        }
        else {
            trainerService.updateTrainer(trainer);
            new ModelAndView("redirect:/trainers");
        }
    }

    // manage trainer team
    @RequestMapping(value="/manage-team", method=RequestMethod.GET)
    public ModelAndView manageTeam(@RequestParam(value="id", required=true) int id) {
        ModelAndView mav = new ModelAndView("pokemonview");
        def trainer = trainerService.getTrainer(id);
        def pokemon = trainerService.getTeam(id);
        Trains2 t = new Trains2();
        t.tid = id;
        mav.addObject("team", true);
        mav.addObject("trainer", trainer);
        mav.addObject("pokemon", pokemon);
        mav.addObject("trains", t);
        return mav;
    }

    // drop pokemon from team
    @RequestMapping(value="/drop", method=RequestMethod.GET)
    public ModelAndView drop(@RequestParam(value="pid", required=true) int pid,
                                   @RequestParam(value="tid", required=true) int tid) {
        trainerService.dropPokemon(pid, tid);
        new ModelAndView("redirect:/manage-team?id=" + tid);
    }

    // add pokemon to team
    @RequestMapping(value="/add-pokemon", method=RequestMethod.POST)
    public ModelAndView add(@ModelAttribute(value="trains") @Valid Trains2 trains, BindingResult bindingResult) {
        trainsValidator.validate(trains, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("pokemonview")
            def trainer = trainerService.getTrainer(trains.tid);
            def pokemon = trainerService.getTeam(trains.tid);
            Trains2 t = new Trains2();
            t.tid = trains.tid;
            mav.addObject("team", true);
            mav.addObject("trainer", trainer);
            mav.addObject("pokemon", pokemon);
            mav.addObject("trains", t);

            if (bindingResult.hasFieldErrors('name')) {
                mav.addObject("error", bindingResult.getFieldError("name").getCode());
            }
            else if (bindingResult.hasFieldErrors('tid')) {
                mav.addObject("error", bindingResult.getFieldError("tid").getCode());
            }
            else {
                mav.addObject("error", "Unknown Error. Please try again.");
            }
            return mav;
        }
        else {
            def pid = pokemonService.getPokemonByName(trains.name.toLowerCase()).getPokedexNum();
            Trains t = new Trains(pid, trains.tid);
            trainerService.addPokemon(t);
            new ModelAndView("redirect:/manage-team?id=" + trains.tid);
        }
    }

    // baby class for populating trains objects with names rather than pids
    static class Trains2 {
        String name;
        Integer tid;

        Trains2() {}
    }
}

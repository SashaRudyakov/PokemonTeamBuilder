package com.pokemon.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.services.TrainerService;
import com.pokemon.services.PokemonService;
import com.pokemon.controllers.TrainerController.Trains2;

@Component
public class TrainsValidator implements Validator {

    @Autowired TrainerService trainerService;
    @Autowired PokemonService pokemonService;

    public boolean supports(Class<?> clazz) {
        return Trains2.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Trains2 p = (Trains2)target;
        String name = p.name;
        Integer tid = p.tid;
        List team = trainerService.getTeam(tid);
        try {
            def poke = pokemonService.getPokemonByName(name.toLowerCase());
            if (team*.pokedexNum.contains(poke.getPokedexNum())) {
                errors.rejectValue("name", "This team already has that pokemon.");
            }
        }
        catch(NullPointerException e) {
            errors.rejectValue("name", "Pokemon does not exist!");
        }
        if (team.size() >= 6) {
            errors.rejectValue("tid", "Team can only have up to 6 pokemon.");
        }
    }
}
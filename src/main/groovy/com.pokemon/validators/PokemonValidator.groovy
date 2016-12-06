package com.pokemon.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.services.MoveService;
import com.pokemon.db.tables.pojos.PokemonP;

@Component
public class PokemonValidator implements Validator {

    @Autowired MoveService moveService;

    public boolean supports(Class<?> clazz) {
        return PokemonP.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        PokemonP p = (PokemonP)target;
        String move1 = p.getMove1();
        String move2 = p.getMove2();
        String move3 = p.getMove3();
        String move4 = p.getMove4();
        try {
            moveService.getMove(move1);
        }
        catch(NullPointerException e) {
            errors.rejectValue("move1", "invalid name for move1");
        }
        try {
            moveService.getMove(move2);
        }
        catch(NullPointerException e) {
            errors.rejectValue("move2", "invalid name for move2");
        }
        try {
            moveService.getMove(move3);
        }
        catch(NullPointerException e) {
            errors.rejectValue("move3", "invalid name for move3");
        }
        try {
            moveService.getMove(move4);
        }
        catch(NullPointerException e) {
            errors.rejectValue("move4", "invalid name for move4");
        }
    }
}
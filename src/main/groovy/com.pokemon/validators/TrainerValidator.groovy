package com.pokemon.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.services.TrainerService;
import com.pokemon.db.tables.pojos.Trainer;

@Component
public class TrainerValidator implements Validator {

    @Autowired TrainerService trainerService;

    public boolean supports(Class<?> clazz) {
        return Trainer.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Trainer p = (Trainer)target;
        String username = p.getUsername();
        Integer age = p.getAge();
        try {
            trainerService.getTrainerByUsername(username)
            errors.rejectValue("username", "username already exists");
        }
        catch(NullPointerException e) {
            // do nothing - we r good
        }

        if (age < 0 || age == null) {
            errors.rejectValue("age", "age cannot be negative");
        }
    }
}
package com.pokemon.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.TypeService;
import com.pokemon.db.tables.pojos.Type
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class TypeController {

    @Autowired TypeService typeService;

    // Show all types
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ModelAndView types() {
        ModelAndView mav = new ModelAndView("typesview");
        mav.addObject("types", typeService.getTypes());
        mav.addObject("type", "");
        mav.addObject("queryType", "");
        return mav;
    }

    // view weaknesses for given type
    @RequestMapping(value = "/weak-against", method = RequestMethod.GET)
    public ModelAndView weaknesses(@RequestParam(value="name", required=true) String name) {
        ModelAndView mav = new ModelAndView("typesview");
        def types = typeService.getWeakAgainst(name);
        mav.addObject("types", types);
        mav.addObject("type", name);
        mav.addObject("queryType", "Weaknesses : ");
        return mav;
    }

    // view strengths for given type
    @RequestMapping(value = "/strong-against", method = RequestMethod.GET)
    public ModelAndView strengths(@RequestParam(value="name", required=true) String name) {
        ModelAndView mav = new ModelAndView("typesview");
        def types = typeService.getStrongAgainst(name);
        mav.addObject("types", types);
        mav.addObject("type", name)
        mav.addObject("queryType", "Strengths : ");
        return mav;
    }
}

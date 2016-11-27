package com.pokemon.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.pokemon.services.MoveService;
import com.pokemon.db.tables.pojos.Move
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid;

@Controller
public class MoveController {

    @Autowired MoveService moveService;

    // show move details
    @RequestMapping(value="/moveDetails", method=RequestMethod.GET)
    public ModelAndView viewMoveDetails(@RequestParam(value="moveName", required=true) String moveName) {
        ModelAndView mav = new ModelAndView("moveDetails");
        Move move = moveService.getMove(moveName);
        mav.addObject("move", move)
        return mav;
    }
}

package com.controller;


import com.model.Mandal;
import com.model.User;
import com.services.MandalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@PropertySources(value = {@PropertySource("classpath:messages.properties")})
@RequestMapping("/mandal")
public class MandalController {

    private MandalService mandalService;

    @Autowired
    public MandalController(MandalService mandalService) {
        this.mandalService = mandalService;
    }

    @RequestMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("mandal/mandalList");
        return modelAndView;
    }

    @RequestMapping(value = "/addMandal", method = RequestMethod.GET)
    public ModelAndView addMandal(ModelAndView modelAndView) {
        modelAndView.addObject("mandal", new Mandal());
        modelAndView.setViewName("mandal/addMandal");
        return modelAndView;

    }

    @RequestMapping(value = "/addMandal", method = RequestMethod.POST)
    public ModelAndView addMandal(@Valid Mandal mandal, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            if (mandalService.isMandalExist(mandal)) {
                modelAndView.addObject("message", "Mandal already Exist");
            } else {
                mandalService.saveMandal(mandal);
                modelAndView.addObject("mandal", new Mandal());
                modelAndView.addObject("message", "Mandal added successfully");
            }
        }
        modelAndView.setViewName("mandal/addMandal");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateMandal(ModelAndView modelAndView, @RequestParam String id) {
        Mandal mandal= mandalService.findByID(id);
        modelAndView.addObject("mandal", mandal);
        modelAndView.setViewName("mandal/updateMandal");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateMandal(@Valid Mandal mandal, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            mandalService.saveMandal(mandal);
            modelAndView.addObject("message", "Mandal Updated Successfully");
        }
        modelAndView.setViewName("mandal/updateMandal");

        return modelAndView;
    }


    @RequestMapping(value = "/delete")
    public ModelAndView deleteMandal(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("deletedMandal", mandalService.deleteMandalById(id).getMandalName());
        modelAndView.addObject("message", "Mandal deleted");
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("mandal/mandalList");
        return modelAndView;
    }


}

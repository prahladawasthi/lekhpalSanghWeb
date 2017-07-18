package com.controller;


import com.model.District;
import com.model.Tahsil;
import com.repository.TahsilRepository;
import com.services.DistrictService;
import com.services.MandalService;
import com.services.TahsilService;
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
@RequestMapping("/tahsil")
public class TahsilController {

    private TahsilService tahsilService;
    private DistrictService districtService;
    private MandalService mandalService;

    @Autowired
    public TahsilController(TahsilService tahsilService, DistrictService districtService, MandalService mandalService) {
        this.tahsilService = tahsilService;
        this.districtService = districtService;
        this.mandalService = mandalService;
    }

    @RequestMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("tahsilList", tahsilService.findAllTahsils());
        modelAndView.setViewName("tahsil/tahsilList");
        return modelAndView;
    }

    @RequestMapping(value = "/addTahsil", method = RequestMethod.GET)
    public ModelAndView addDistrict(ModelAndView modelAndView) {
        modelAndView.addObject("tahsil", new Tahsil());
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("tahsil/addTahsil");
        return modelAndView;

    }

    @RequestMapping(value = "/addTahsil", method = RequestMethod.POST)
    public ModelAndView addDistrict(@Valid Tahsil tahsil, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            if (tahsilService.isTahsilExist(tahsil)) {

                modelAndView.addObject("message", "Tahsil already Exist in same District");
            } else {
                tahsil.setMandalID(districtService.findByID(tahsil.getDistrictID()).getMandalID());
                tahsilService.saveTahsil(tahsil);
                modelAndView.addObject("tahsil", new Tahsil());
                modelAndView.addObject("message", "Tahsil added successfully");
            }
        }
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("tahsil/addTahsil");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateDistrict(ModelAndView modelAndView, @RequestParam String id) {
        Tahsil tahsil = tahsilService.findByID(id);
        modelAndView.addObject("tahsil", tahsil);
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("tahsil/updateTahsil");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateDistrict(@Valid Tahsil tahsil, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            tahsil.setMandalID(districtService.findByID(tahsil.getDistrictID()).getMandalID());
            tahsilService.saveTahsil(tahsil);
            modelAndView.addObject("message", "Tahsil Updated Successfully");
        }
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("tahsil/updateTahsil");

        return modelAndView;
    }

    @RequestMapping(value = "/viewTahsil")
    public ModelAndView viewTahsil(ModelAndView modelAndView, @RequestParam String id) {
        Tahsil tahsil = tahsilService.findByID(id);
        modelAndView.addObject("tahsil", tahsil);
        modelAndView.addObject("districtName", districtService.findByID(tahsil.getDistrictID()).getDistrictName());
        modelAndView.addObject("mandalName", mandalService.findByID(tahsil.getMandalID()).getMandalName());

        modelAndView.setViewName("tahsil/viewTahsil");

        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView deleteTahsil(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("deletedTahsil", tahsilService.deleteTahsilById(id).getTahsilName());
        modelAndView.addObject("message", "Tahsil deleted");
        modelAndView.addObject("tahsilList", tahsilService.findAllTahsils());
        modelAndView.setViewName("tahsil/tahsilList");
        return modelAndView;
    }
}

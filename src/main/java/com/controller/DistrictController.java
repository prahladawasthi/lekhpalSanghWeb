package com.controller;


import com.model.District;
import com.services.DistrictService;
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
@RequestMapping("/district")
public class DistrictController {

    private DistrictService districtService;
    private MandalService mandalService;

    @Autowired
    public DistrictController(DistrictService districtService, MandalService mandalService) {
        this.districtService = districtService;
        this.mandalService = mandalService;
    }

    @RequestMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("district/districtList");
        return modelAndView;
    }

    @RequestMapping(value = "/addDistrict", method = RequestMethod.GET)
    public ModelAndView addDistrict(ModelAndView modelAndView) {
        modelAndView.addObject("district", new District());
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("district/addDistrict");
        return modelAndView;

    }

    @RequestMapping(value = "/addDistrict", method = RequestMethod.POST)
    public ModelAndView addDistrict(@Valid District district, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            if (districtService.isDistrictExist(district)) {
                modelAndView.addObject("message", "District already Exist in same Mandal");
            } else {
                districtService.saveDistrict(district);
                modelAndView.addObject("district", new District());
                modelAndView.addObject("message", "District added successfully");
            }
        }
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("district/addDistrict");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateDistrict(ModelAndView modelAndView, @RequestParam String id) {
        District district = districtService.findByID(id);
        modelAndView.addObject("district", district);
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("district/updateDistrict");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateDistrict(@Valid District district, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            districtService.saveDistrict(district);
            modelAndView.addObject("message", "District Updated Successfully");
        }
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.setViewName("district/updateDistrict");

        return modelAndView;
    }

    @RequestMapping(value = "/viewDistrict")
    public ModelAndView viewDistrict(ModelAndView modelAndView, @RequestParam String id) {
        District district = districtService.findByID(id);
        modelAndView.addObject("district", district);
        modelAndView.addObject("mandalName", mandalService.findByID(district.getMandalID()).getMandalName());

        modelAndView.setViewName("district/viewDistrict");

        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView deleteDistrict(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("deletedDistrict", districtService.deleteDistrictById(id).getDistrictName());
        modelAndView.addObject("message", "District deleted");
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.setViewName("district/districtList");
        return modelAndView;
    }
}

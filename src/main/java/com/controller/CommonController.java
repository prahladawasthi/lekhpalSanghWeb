package com.controller;

import com.login.MongoUserDetails;
import com.model.Tahsil;
import com.model.User;
import com.services.DistrictService;
import com.services.MandalService;
import com.services.TahsilService;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@PropertySources(value = {@PropertySource("classpath:messages.properties")})
@RequestMapping("/common")
public class CommonController {

    private UserService userService;
    private TahsilService tahsilService;
    private DistrictService districtService;
    private MandalService mandalService;

    @Value("${user.updated}")
    private String userUpdatedSuccessfully;


    @Autowired
    public CommonController(UserService userService, MandalService mandalService, DistrictService districtService, TahsilService tahsilService) {
        this.userService = userService;
        this.mandalService = mandalService;
        this.districtService = districtService;
        this.tahsilService = tahsilService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView userHome(ModelAndView modelAndView) {
        MongoUserDetails loggedInUser = (MongoUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Optional<User> optional = userService.findUserByEmail(loggedInUser.getEmail());
        User user = optional.get();

        modelAndView.addObject("mandalName", mandalService.findByID(user.getMandalID()).getMandalName());
        modelAndView.addObject("districtName", districtService.findByID(user.getDistrictID()).getDistrictName());
        modelAndView.addObject("tahsilName", tahsilService.findByID(user.getTahsilID()).getTahsilName());

        modelAndView.addObject("user", user);
        modelAndView.setViewName("common/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateUser(ModelAndView modelAndView, @RequestParam String id) {
        User user = userService.findByID(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("userRoles", userService.findUserRoles());
        modelAndView.addObject("tahsilList", tahsilService.findAllTahsils());
        modelAndView.setViewName("admin/updateUser");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@Valid User user, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            Tahsil tahsil = tahsilService.findByID(user.getTahsilID());
            user.setMandalID(tahsil.getMandalID());
            user.setDistrictID(tahsil.getDistrictID());
            userService.saveUser(user);
            modelAndView.addObject("message", userUpdatedSuccessfully);
        }

        modelAndView.addObject("userRoles", userService.findUserRoles());
        modelAndView.addObject("tahsilList", tahsilService.findAllTahsils());
        modelAndView.setViewName("admin/updateUser");

        return modelAndView;
    }
}

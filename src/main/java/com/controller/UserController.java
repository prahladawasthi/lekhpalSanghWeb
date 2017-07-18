package com.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PropertySources(value = {@PropertySource("classpath:messages.properties")})
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView userHome(ModelAndView modelAndView) {
        modelAndView.setViewName("user/user");
        return modelAndView;
    }
}

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
import java.util.HashMap;
import java.util.Map;
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

    @RequestMapping("/search")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("mandalID", "");
        modelAndView.addObject("districtID", "");
        modelAndView.addObject("tahsilID", "");
        modelAndView.addObject("userList", userService.findAllUsers());
        modelAndView.addObject("mandalList", mandalService.findAllMandals());
        modelAndView.addObject("districtList", districtService.findAllDistricts());
        modelAndView.addObject("tahsilList", tahsilService.findAllTahsils());
        modelAndView.setViewName("common/search");
        return modelAndView;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView userHome(ModelAndView modelAndView, @RequestParam String id) {

        User user = userService.findByID(id);

        if (null != SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            MongoUserDetails loggedInUser = (MongoUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Optional<User> optional = userService.findUserByEmail(loggedInUser.getEmail());
            if (optional.isPresent() && null != user.getRatingProvider() && user.getRatingProvider().containsKey(optional.get().getId())) {
                modelAndView.addObject("alreadyRated", "Yes");
            } else {
                modelAndView.addObject("alreadyRated", "No");
            }

        }


        if (null != user.getMandalID())
            modelAndView.addObject("mandalName", mandalService.findByID(user.getMandalID()).getMandalName());

        if (null != user.getDistrictID())
            modelAndView.addObject("districtName", districtService.findByID(user.getDistrictID()).getDistrictName());

        if (null != user.getTahsilID())
            modelAndView.addObject("tahsilName", tahsilService.findByID(user.getTahsilID()).getTahsilName());

        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/userProfile");
        return modelAndView;

    }

    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    public ModelAndView rateUser(ModelAndView modelAndView, @RequestParam String id, @RequestParam String rating) {
        User user = userService.findByID(id);

        MongoUserDetails loggedInUser = (MongoUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> optional = userService.findUserByEmail(loggedInUser.getEmail());

        if (optional.isPresent()) {
            if (null == user.getRatingProvider()) {
                Map<String, String> ratingMap = new HashMap<String, String>();
                ratingMap.put(optional.get().getId(), rating);
                user.setRatingProvider(ratingMap);
                user.setRating(rating);
            } else if (!user.getRatingProvider().containsKey(optional.get().getId())) {
                user.getRatingProvider().put(optional.get().getId(), rating);
                int voter = 0;
                int rate = 0;
                for (Map.Entry entry : user.getRatingProvider().entrySet()) {
                    rate += Integer.parseInt(String.valueOf(entry.getValue()));
                    voter++;
                }
                user.setRating(String.valueOf(Math.round(rate / voter)));
            } else {
                modelAndView.addObject("message", "You have already voted for him");
            }
            userService.saveUser(user);
            modelAndView.addObject("alreadyRated", "Yes");
        }

        modelAndView.addObject("participantNames", "Thank you for the rating");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/userProfile");
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getProfile(ModelAndView modelAndView) {

        MongoUserDetails loggedInUser = (MongoUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Optional<User> optional = userService.findUserByEmail(loggedInUser.getEmail());
        User user = null;

        if (optional.isPresent()) {
            user = optional.get();
        }

        if (null != user.getMandalID())
            modelAndView.addObject("mandalName", mandalService.findByID(user.getMandalID()).getMandalName());

        if (null != user.getDistrictID())
            modelAndView.addObject("districtName", districtService.findByID(user.getDistrictID()).getDistrictName());

        if (null != user.getTahsilID())
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
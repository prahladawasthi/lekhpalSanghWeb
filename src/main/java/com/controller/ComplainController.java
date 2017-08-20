package com.controller;

import com.model.Complain;
import com.services.ComplainService;
import com.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/complain")
public class ComplainController {

    private ComplainService complainService;
    private EmailService emailService;

    @Autowired
    public ComplainController(ComplainService complainService, EmailService emailService) {
        this.complainService = complainService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView complain(ModelAndView modelAndView) {
        modelAndView.addObject("complain", new Complain());
        modelAndView.setViewName("complain/complain");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView feedback(@Valid Complain complain, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            complainService.saveComplain(complain);
            modelAndView.addObject("message", "Your complain has been submitted");

            try {
                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(complain.getComplainFrom());
                email.setBcc("lekhpal.up@gmail.com");

                email.setSubject("Thank you for raising the complain");
                email.setText("Your complain has been submitted. \n" +"We will review and then forward your complain to the relevant authority");
                email.setFrom(complain.getComplainFrom());
                emailService.sendEmail(email);
            } catch (MailException exception) {
                modelAndView.addObject("errorMessage", "We are unable to accept your complain due to : "
                        + exception.getMessage());
            }

        }
        modelAndView.addObject("complain", new Complain());
        modelAndView.setViewName("complain/complain");
        return modelAndView;
    }

    @RequestMapping("/deleteComplain")
    public ModelAndView deleteComplain(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("deletedComplain", complainService.deleteComplainById(id).getComplainSubject());
        modelAndView.addObject("message", "Complain has been deleted");
        modelAndView.addObject("complainList", complainService.findAllComplains());
        modelAndView.setViewName("complain/complainList");
        return modelAndView;
    }

    @RequestMapping("/complainList")
    public ModelAndView complainList(ModelAndView modelAndView) {
        modelAndView.addObject("complainList", complainService.findAllComplains());
        modelAndView.setViewName("complain/complainList");
        return modelAndView;
    }

    @RequestMapping(value = "/viewComplain")
    public ModelAndView viewFeedback(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("complain", complainService.findByID(id));
        modelAndView.setViewName("complain/viewComplain");
        return modelAndView;
    }
}

package com.controller;

import com.model.Feedback;
import com.services.EmailService;
import com.services.FeedService;
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
@RequestMapping("/feedback")
public class FeedbackController {

    private FeedService feedService;
    private EmailService emailService;

    @Autowired
    public FeedbackController(FeedService feedService, EmailService emailService) {
        this.feedService = feedService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView feedback(ModelAndView modelAndView) {
        modelAndView.addObject("feed", new Feedback());
        modelAndView.setViewName("feedback/feed");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView feedback(@Valid Feedback feed, BindingResult bindingResult, ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            feedService.saveFeed(feed);
            modelAndView.addObject("message", "Thank you for the feedback.");
            try {
                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(feed.getUserEmail());
                email.setSubject("Thank you for the Feedback");
                email.setText("Thank you for the feedback. /n We are in progress to upgrade this application to serve you better");
                email.setFrom("noreply@lekhpal.co.in");
                emailService.sendEmail(email);
            } catch (MailException exception) {
                modelAndView.addObject("errorMessage", "We are unable to email you due to : " + exception.getMessage());
            }

        }
        modelAndView.addObject("feed", new Feedback());
        modelAndView.setViewName("feedback/feed");
        return modelAndView;
    }

    @RequestMapping("/deleteFeed")
    public ModelAndView deleteFeedback(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("deletedFeed", feedService.deleteFeedById(id).getUserEmail());
        modelAndView.addObject("message", "Feed back has been deleted");
        modelAndView.addObject("feedList", feedService.findAllFeeds());
        modelAndView.setViewName("feedback/feedList");
        return modelAndView;
    }

    @RequestMapping("/feedList")
    public ModelAndView feedbackList(ModelAndView modelAndView) {
        modelAndView.addObject("feedList", feedService.findAllFeeds());
        modelAndView.setViewName("feedback/feedList");
        return modelAndView;
    }

    @RequestMapping(value = "/viewFeed")
    public ModelAndView viewFeedback(ModelAndView modelAndView, @RequestParam String id) {
        modelAndView.addObject("feed", feedService.findByID(id));
        modelAndView.setViewName("feedback/viewFeed");
        return modelAndView;
    }
}

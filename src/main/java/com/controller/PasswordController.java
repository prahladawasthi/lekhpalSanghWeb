package com.controller;

import com.login.MongoUserDetails;
import com.model.User;
import com.services.EmailService;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordController {

    private UserService userService;
    private EmailService emailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PasswordController(UserService userService, EmailService emailService,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Display forgotPassword page
    @RequestMapping(value = "/common/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("common/forgotPassword");
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/common/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail,
                                                  HttpServletRequest request) {

        // Lookup user in database by e-mail
        Optional<User> optional = userService.findUserByEmail(userEmail);

        if (!optional.isPresent()) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password
            User user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            try {
                SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
                passwordResetEmail.setFrom("support@demo.com");
                passwordResetEmail.setTo(user.getEmail());
                passwordResetEmail.setSubject("Password Reset Request");
                passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
                        + user.getResetToken());

                emailService.sendEmail(passwordResetEmail);
            }catch (Exception e){
                modelAndView.addObject("emailMessage", "We are unable to send email due to "+ e.getMessage());

            }
            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("common/forgotPassword");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        Optional<User> user = userService.findUserByResetToken(token);

        if (user.isPresent()) { // Token found in DB
            modelAndView.addObject("resetToken", token);
        } else { // Token not found in DB
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("common/resetPassword");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, BindingResult bindingResult,
                                       @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        if (!requestParams.get("password").equals(requestParams.get("ConfirmPassword"))) {

            bindingResult.reject("password");

            redir.addFlashAttribute("errorMessage", "Both passwords must be identical.");

            modelAndView.setViewName("redirect:reset?token=" + requestParams.get("token"));
            System.out.println(requestParams.get("token"));
            return modelAndView;
        }

        // Find the user associated with the reset token
        Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));

        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            User resetUser = user.get();

            // Set new password
            resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            userService.saveUser(resetUser);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:common/login");
            return modelAndView;

        } else {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("common/resetPassword");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:common/login");
    }

    // Display form to change password
    @RequestMapping(value = "/common/changePassword", method = RequestMethod.GET)
    public ModelAndView displayChangePasswordPage(ModelAndView modelAndView) {
        modelAndView.addObject("notLoggedIn",
                !SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        modelAndView.setViewName("common/changePassword");
        return modelAndView;
    }

    // Process change password form
    @RequestMapping(value = "/common/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(ModelAndView modelAndView, BindingResult bindingResult,
                                       @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        if (!requestParams.get("password").equals(requestParams.get("ConfirmPassword"))) {
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Both passwords must be identical.");
            modelAndView.setViewName("redirect:common/changePassword");

            return modelAndView;
        }

        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            redir.addFlashAttribute("successMessage", "Please login to change your password");
            modelAndView.setViewName("redirect:common/login");
            return modelAndView;
        } else {
            MongoUserDetails user = (MongoUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            Optional<User> optional = userService.findUserByEmail(user.getEmail());
            if (optional.isPresent()) {
                User changeUser = optional.get();
                changeUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
                userService.saveUser(changeUser);
                redir.addFlashAttribute("successMessage", "You have successfully changed your password.");
                modelAndView.setViewName("redirect:profile");
                return modelAndView;
            } else {
                redir.addFlashAttribute("errorMessage", "We were unable to find your registration with us.");
                modelAndView.setViewName("profile");
                return modelAndView;
            }
        }
    }
}

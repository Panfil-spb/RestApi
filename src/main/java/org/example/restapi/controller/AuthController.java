package org.example.restapi.controller;

import org.example.restapi.model.User;
import org.example.restapi.service.RegistrationService;
import org.example.restapi.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login";
    }

    @RequestMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrationService.register(user);
        return "redirect:/auth/login";
    }


}

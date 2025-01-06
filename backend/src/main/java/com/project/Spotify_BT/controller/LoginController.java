package com.project.Spotify_BT.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    

    @GetMapping("/login")
    public String showLoginPage() {
        logger.debug("Showing the login page");
        return "redirect:/loginPage.html";
    }

    @GetMapping("/success")
    public String successPage() {
        return "redirect:/success.html";
    }


    @GetMapping("/error")
    public String showErrorPage() {
        return "redirect:/error.html";
    }

}
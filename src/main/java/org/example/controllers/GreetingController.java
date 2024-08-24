package org.example.controllers;

import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

@Controller
public class GreetingController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String greetingUser(Model model){
        model.addAttribute("date", LocalDate.now().toString());
        return "/greeting";
    }
}

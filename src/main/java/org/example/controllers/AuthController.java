package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.models.UserPackage.Role;
import org.example.models.UserPackage.Status;
import org.example.models.UserPackage.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "/authorization/auth";
    }

    @GetMapping("/success")
    public String getSuccessPage(){
        return "/authorization/success";
    }

    @PostMapping("/logout")
    public String getLogOut(){
        return "redirect:/authorization/auth";
    }

    @GetMapping("/registration")
    public String getRegistryPage(){
        return "/authorization/registration";
    }

    @PostMapping("/registry")
    public String registryNewUser(@RequestParam("register-username") String username,
                                  @RequestParam("register-password") String password,
                                  @RequestParam("register-email") @Valid String email){

        if (userService.existsUserByUsername(username) || userService.existsUserByEmail(email)){
            log.info("ТАКОЙ ПОЛЬЗОВАТЕЛЬ УЖЕ СУЩЕСТВУЕТ!!!!!!!!!!!!!!!!!!!!!!!");
        } else {
            User userNew = new User();
            userNew.setUsername(username);
            userNew.setPassword(new BCryptPasswordEncoder(12).encode(password));
            userNew.setEmail(email);
            userNew.setUserChats(new ArrayList<>());
            userNew.setRole(Role.USER);
            userService.add(userNew);
            log.info("USER CREATING...." + userNew.getUsername() + " " + userNew.getPassword());
        }
        return "redirect:/authorization/auth";
    }
}

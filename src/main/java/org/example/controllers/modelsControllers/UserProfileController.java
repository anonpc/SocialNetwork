package org.example.controllers.modelsControllers;

import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.example.services.ChatService;
import org.example.services.UserProfileService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

}

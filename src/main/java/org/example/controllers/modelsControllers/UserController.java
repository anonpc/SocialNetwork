package org.example.controllers.modelsControllers;

import org.example.models.Chat;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public String homePage(Model model) {
        User user = getCurrentUser();

        model.addAttribute("user", user);
        model.addAttribute("allChats", chatService.findAll());
        model.addAttribute("creatorOfChats", chatService.findCreator(user.getId()));
        return "/user/profile";
    }

    @GetMapping("/allUsers")
    public String getAllUsersPage(Model model){
        model.addAttribute("allUsers", userService.getAll());
        return "/user/allUsers";
    }

    //ДОБАВЛЕНИЕ ЧАТА ПОЛЬЗОВАТЕЛЕМ
    @GetMapping("/createChatPage")
    public String showCreateChatPage(Model model) {
        User user = getCurrentUser();

        model.addAttribute("id", user.getId());
        model.addAttribute("chat", new Chat());
        return "/user/createChat";
    }

    @PostMapping("/createChat")
    public String create(@ModelAttribute("chat") Chat chat, @RequestParam(name = "idUser") Long userId) {
        chatService.create(chat, userId);
        return "redirect:/profile";
    }

    //Получение авторизованного пользователя
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUser(userService.getByUsername(username));
        return userProfile.getUser();
    }
}

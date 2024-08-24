package org.example.controllers.modelsControllers;

import lombok.Data;
import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.example.services.ChatGptService;
import org.example.services.UserProfileService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Data
public class ChatGPTController {
    private final ChatGptService chatGptService;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Autowired
    public ChatGPTController(ChatGptService chatGptService, UserService userService, UserProfileService userProfileService) {
        this.chatGptService = chatGptService;
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile/chatGpt")
    public String getChatGPTPage(Model model) {
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("chatResponse", getCurrentUser().getChatGptMessageHistoryList());
        return "chatGPT/chatGpt";
    }

    @PostMapping("/profile/chatGptMessaging")
    public String getChatGptResponse(@RequestParam(name = "messageToGpt") String text){
        chatGptService.createMessageFromChatGpt(text, getCurrentUser());
        return "redirect:/profile/chatGpt";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUser(userService.getByUsername(username));
        return userProfile.getUser();
    }
}

package org.example.controllers.modelsControllers;

import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.example.services.ChatService;
import org.example.services.MessageService;
import org.example.services.UserProfileService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/profile/chat/join/{chat_id}/sendMessage")
    public String createMessage(@PathVariable(name = "chat_id") Long chat_id,
                                @RequestParam(name = "message") String text) {

        messageService.create(text, getCurrentUser().getId(), chat_id);

        return "redirect:/profile/chat/join/" + chat_id;
    }
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUser(userService.getByUsername(username));
        return userProfile.getUser();
    }
}

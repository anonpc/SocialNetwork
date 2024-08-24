package org.example.controllers.modelsControllers;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile/chat/{chat_id}")
    public String infoChat(@PathVariable(name = "chat_id") Long chat_id, Model model) {
        model.addAttribute("chat", chatService.getById(chat_id));
        model.addAttribute("user", userService.getById(getCurrentUser().getId()));
        return "chat/info";
    }

    @GetMapping("/profile/chat/join/{chat_id}")
    public String showChatJoinPage(@PathVariable(name = "chat_id") Long chat_id, Model model) {

        Chat chat = chatService.getById(chat_id);

        model.addAttribute("user", userService.getById(getCurrentUser().getId()));
        model.addAttribute("chat", chat);
        model.addAttribute("allMessagesInChat", chat.getMessages());

        return "/chat/messenger";
    }

    @PostMapping("/profile/chat/update/{chat_id}")
    public String updateChat(@PathVariable(name = "chat_id") Long chat_id,
                             @RequestParam(name = "newName") String name) {
        chatService.update(chat_id, new Chat(name));
        return "redirect:/profile";
    }

    @PostMapping("/profile/chat/delete/{chat_id}")
    public String deleteChat(@PathVariable(name = "chat_id") Long chat_id) {
        chatService.deleteById(chat_id);
        return "redirect:/profile";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUser(userService.getByUsername(username));
        return userProfile.getUser();
    }
}

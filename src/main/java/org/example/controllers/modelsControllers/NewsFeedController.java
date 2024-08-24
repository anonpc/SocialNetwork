package org.example.controllers.modelsControllers;

import lombok.extern.slf4j.Slf4j;
import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.example.services.NewsFeedService;
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
@Slf4j
public class NewsFeedController {

    private final NewsFeedService newsFeedService;
    private final UserProfileService userProfileService;
    private final UserService userService;

    @Autowired
    public NewsFeedController(NewsFeedService newsFeedService,
                              UserProfileService userProfileService,
                              UserService userService) {
        this.newsFeedService = newsFeedService;
        this.userProfileService = userProfileService;
        this.userService = userService;
    }


    //СОЗДАНИЕ НОВОСТНОГО ПОСТА
    @GetMapping("/profile/newsFeed")
    public String getNewsFeedPage(Model model){
        model.addAttribute("allNews", newsFeedService.getAll());
        return "/NewsFeed/newsFeed";
    }

    @PostMapping("/profile/createPost")
    public String createNewPost(@RequestParam(name = "content-text") String content){
        newsFeedService.createNewPost(content, getCurrentUser());
        return "redirect:/profile/newsFeed";
    }

    @GetMapping("/profile/createPostPage")
    public String showCreateNewsFeedPage(){
        return "/NewsFeed/createPost";
    }

    //Получение авторизованного пользователя
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUser(userService.getByUsername(username));
        return userProfile.getUser();
    }
}

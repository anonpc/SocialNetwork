//
//
//@Controller
//public class ProfileController {
//    @Autowired
//    private UserProfileService userProfileService;
//
//    @GetMapping("/profile/{id}")
//    public String profile(@PathVariable Long id, Model model){
//        UserProfile user = userProfileService.findById(id);
//        model.addAttribute("user", user);
//        return "profile";
//    }
//}
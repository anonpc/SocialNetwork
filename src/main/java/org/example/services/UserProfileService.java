package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.example.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile getUserProfileByUser(User user) {
        UserProfile userProfileBuf = userProfileRepository.getUserProfileByUser(user).orElse(null);

        if (userProfileBuf == null) {
            create(new UserProfile(), user);
        }

        return userProfileRepository.getUserProfileByUser(user).orElseThrow(()
                -> new IllegalArgumentException("SOMETHING WAS WRONG!!!")
        );
    }

    public void create(UserProfile userProfile, User user) {
        userProfile.setUser(user);
        userProfile.setProfileName(user.getUsername());
        user.setUserProfile(userProfile);
        userProfileRepository.save(userProfile);
    }
}

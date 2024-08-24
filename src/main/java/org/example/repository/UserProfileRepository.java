package org.example.repository;

import org.example.models.UserPackage.User;
import org.example.models.UserPackage.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> getUserProfileByUser(User user);
}

package org.example.repository;

import org.example.models.UserPackage.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    void deleteUserById(Long id);
}

package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.models.Chat;
import org.example.models.UserPackage.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        if (user != null) {
            log.info("Creating new {}", user);
            return userRepository.save(user);
        } else
            log.info("Creating new user failed! User is null!!!");
        return null;
    }

    public User getById(Long id) {
        log.info("Getting by ID user {}", id);
        log.info("ListOfUser is {}", userRepository.findById(id).orElse(null).getUserChats());
        return userRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("User with ID:" + id + " not found!"));
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()
        -> new IllegalArgumentException("User with Username:" + username + " not found!"
        ));
    }

    public List<User> getAll() {
        Iterable<User> userIterable = userRepository.findAll();
        List<User> userList = new ArrayList<>();

        userIterable.forEach(userList::add);

        return userList;
    }

    public Boolean existsUserByUsername(String username){
        return userRepository.existsUserByUsername(username);
    }

    public Boolean existsUserByEmail(String username){
        return userRepository.existsUserByEmail(username);
    }

    public void deleteUser(Long id){
        userRepository.deleteUserById(id);
    }
}

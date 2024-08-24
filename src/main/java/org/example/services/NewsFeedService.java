package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.models.NewsFeed;
import org.example.models.UserPackage.User;
import org.example.repository.NewsFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class NewsFeedService {
    private final NewsFeedRepository newsFeedRepository;

    @Autowired
    public NewsFeedService(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

    public List<NewsFeed> getAll() {

        Iterable<NewsFeed> newsFeedIterable = newsFeedRepository.findAll();

        List<NewsFeed> newsFeedList = new ArrayList<>();
        newsFeedIterable.forEach(newsFeedList::add);

        return newsFeedList;
    }

    public void createNewPost(String content, User user) {
        NewsFeed newsFeed = new NewsFeed();

        newsFeed.setUser(user);
        newsFeed.setContent(content);

        user.getNewsFeed().add(newsFeed);

        log.info("Creating new {}", newsFeed);
        newsFeedRepository.save(newsFeed);
    }
}

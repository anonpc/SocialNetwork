package org.example.repository;

import org.example.models.NewsFeed;
import org.example.models.UserPackage.User;
import org.springframework.data.repository.CrudRepository;

public interface NewsFeedRepository extends CrudRepository<NewsFeed, Long> {
    public Iterable<NewsFeed> getAllByUser(User user);
}

package org.example.repository;

import org.example.models.ChatGptMessageHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGptMessageHistoryRepository extends CrudRepository<ChatGptMessageHistory, Long> {
}

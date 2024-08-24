package org.example.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.models.ChatGptMessageHistory;
import org.example.models.UserPackage.User;
import org.example.repository.ChatGptMessageHistoryRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@Data
@Slf4j
public class ChatGptService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatGptMessageHistoryRepository chatGptMessageHistoryRepository;
    private static final String OPEN_API_KEY = "sk-Rgv41IhGPgtWrCxer034T3BlenkAI9KPApCn69QBCVulcZovK";
    private static final String OPEN_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";

    public String getChatResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(OPEN_API_KEY);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("prompt", userMessage);
        body.add("max_tokens", "50");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                OPEN_API_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody().get("choices")).toString();
        } else {
            throw new RuntimeException("Error communicating with ChatGPT API");
        }
    }

    public void createMessageFromChatGpt(String userMessage, User user){
        String chatGptResponse = getChatResponse(userMessage);

        ChatGptMessageHistory chatGptMessageHistory = new ChatGptMessageHistory();
        chatGptMessageHistory.setText(chatGptResponse);
        chatGptMessageHistory.setUser(user);

        log.info("Creating MessageFromGpt {}", chatGptResponse);


        chatGptMessageHistoryRepository.save(chatGptMessageHistory);
        user.getChatGptMessageHistoryList().add(chatGptMessageHistory);
        log.info("Adding ChatGpt response to User historyOfMessageList {}", user);
    }
}

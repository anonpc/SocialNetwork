package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Chat;
import org.example.models.UserPackage.User;
import org.example.repository.ChatRepository;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void create(Chat chat, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("User with ID " + userId + " not founded!"));

        if (!chat.getChatUsers().contains(user)) {
            chat.getChatUsers().add(user);
        }
        if (!user.getUserChats().contains(chat)) {
            user.getUserChats().add(chat);
        }

        log.info("Creating new {}", chat);
        chatRepository.save(chat);
    }

    public void update(Long id, Chat newChat) {
        Chat oldChat = chatRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Chat with ID" + id + " not founded!"));
        if (newChat.getName() != null) {
            oldChat.setName(newChat.getName());
        }
        if (newChat.getChatUsers() != null) {
            oldChat.setChatUsers(newChat.getChatUsers());
        }
        log.info("Updating chat with ID: " + id + " new fields are Name:" + newChat.getName() + "/ChatUsers:" + newChat.getChatUsers());
        chatRepository.save(oldChat);
    }

    public void deleteById(Long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Chat with ID" + id + "not founded!"));

        //        chat.getChatUsers().get(0).getUserChats().remove(chat);

        //удаление чата у каждого привязаного к нему пользователя
        chat.getChatUsers().forEach(user ->
                user.getUserChats().remove(chat)
        );

        //удаление всех сообщений, привязаных к чату
        chat.getMessages().forEach(message -> message.setUser(null));
        messageRepository.deleteAll(chat.getMessages());


        log.info("Removal chat with ID: " + id);
        chatRepository.deleteById(id);
    }

    public List<Chat> findAll(){
        Iterable<Chat> chatIterable = chatRepository.findAll();
        List<Chat> chatList = new ArrayList<>();

        chatIterable.forEach(chatList::add);

        return chatList;
    }

    public Chat getById(Long id) {
        log.info("Getting Chat by ID: " + id);
        return chatRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("Chat with ID" + id + "not founded!"));
    }

    public List<Chat> findCreator(Long id){
        List<Chat> resultList = new ArrayList<>();
        chatRepository.findAll().forEach(chat -> {
            try {
                if (chat.getChatUsers().get(0).getId().equals(id)) {
                    resultList.add(chat);
                }
            } catch (IndexOutOfBoundsException e){
                log.info(e.toString());
            }
        });
        return resultList;
    }
}

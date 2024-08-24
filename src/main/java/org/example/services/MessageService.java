package org.example.services;

import org.example.models.Chat;
import org.example.models.Message;
import org.example.models.UserPackage.User;
import org.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    public void create(String text, Long user_id, Long chat_id) {
        User user = userService.getById(user_id);
        Chat chat = chatService.getById(chat_id);

        Message message = new Message();

        message.setText(text);
        message.setUser(user);
        message.setChat(chat);

        user.getMessages().add(message);
        chat.getMessages().add(message);
        if (!chat.getChatUsers().contains(user) && !user.getUserChats().contains(chat)) {
            chat.getChatUsers().add(user);
            user.getUserChats().add(chat);
        }
        messageRepository.save(message);
    }
}

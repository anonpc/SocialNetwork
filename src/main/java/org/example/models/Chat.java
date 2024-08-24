package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.models.UserPackage.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat")
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate dateOfCreated;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "userChats")
    private List<User> chatUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    public Chat(String name) {
        this.name = name;
    }

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now().toLocalDate();
    }

    public Chat() {
    }
}

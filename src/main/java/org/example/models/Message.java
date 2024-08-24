package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.models.UserPackage.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private LocalDate timeOfSend;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Chat chat;


    @PrePersist
    private void init() {
        timeOfSend = LocalDateTime.now().toLocalDate();
    }

    public Message() {
    }
}

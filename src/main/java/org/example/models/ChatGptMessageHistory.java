package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.models.UserPackage.User;

@Entity
@Data
public class ChatGptMessageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}

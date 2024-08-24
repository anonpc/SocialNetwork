package org.example.models.UserPackage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.models.Chat;
import org.example.models.ChatGptMessageHistory;
import org.example.models.Message;
import org.example.models.NewsFeed;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @Email(message = "Invalid email format!")
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private LocalDate dateOfCreated;

    @OneToOne
    private UserProfile userProfile;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Chat> userChats = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @OneToMany(mappedBy = "user")
    private List<NewsFeed> newsFeed;

    @OneToMany(mappedBy = "user")
    private List<ChatGptMessageHistory> chatGptMessageHistoryList;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now().toLocalDate();
        status = Status.ACTIVE;
    }

    @Override
    public String toString() {
        return username;
    }
}

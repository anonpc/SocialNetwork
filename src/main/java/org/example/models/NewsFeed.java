package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.models.UserPackage.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "newsFeed")
@Data
@ToString

public class NewsFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "CONTENT CAN'T BE NULL!!! FIX THAT IMMEDIATELY")
    private String content;
    private LocalDate dateOfCreated;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now().toLocalDate();
    }
}

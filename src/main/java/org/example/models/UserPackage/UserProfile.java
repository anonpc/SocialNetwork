package org.example.models.UserPackage;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "userprofile")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String profileName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", user=" + user.getId() + // Вместо user.toString()
                ", profileName=" + user.getUsername() +
                '}';
    }
}

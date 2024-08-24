package org.example.security;

import lombok.Data;
import org.example.models.UserPackage.Status;
import org.example.models.UserPackage.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {
    private final String username;
    private final String password;
    private final Boolean isActive;

    private final List<SimpleGrantedAuthority> grantedAuthorities;

    public SecurityUser(String username, String password, Boolean isActive, List<SimpleGrantedAuthority> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user){
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                Collections.singleton(user.getRole().getAuthorities())
        );
    }
}

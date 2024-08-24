package org.example.models.UserPackage;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER,
    ADMIN;

    public SimpleGrantedAuthority getAuthorities() {
        return new SimpleGrantedAuthority(name());
    }
}

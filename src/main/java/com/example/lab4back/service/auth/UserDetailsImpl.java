package com.example.lab4back.service.auth;

import com.example.lab4back.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    private User user;
    private final boolean isAccountNonExpired = true;
    private final boolean isAccountNonLocked = true;
    private final boolean isCredentialsNonExpired = true;
    private final boolean isEnabled = true;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user);
    }

    public User getUser() {
        return this.user;
    }

    public void setUsername(String username) {
        this.user.setUsername(username);
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public long getId() {
        return this.user.getId();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}

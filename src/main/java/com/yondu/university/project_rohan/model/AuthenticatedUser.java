package com.yondu.university.project_rohan.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails {
    private User user;

    public AuthenticatedUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }

}

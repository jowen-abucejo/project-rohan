package com.yondu.university.project_rohan.entity;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails {
    private User user;

    public AuthenticatedUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().replace(' ', '_'))));
        return authorities;
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

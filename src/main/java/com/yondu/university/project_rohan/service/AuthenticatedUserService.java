package com.yondu.university.project_rohan.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.model.AuthenticatedUser;
import com.yondu.university.project_rohan.repository.UserRepository;

@Service
public class AuthenticatedUserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * @param userRepository
     */
    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(AuthenticatedUser::new).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }

}

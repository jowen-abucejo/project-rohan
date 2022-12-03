package com.yondu.university.project_rohan.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.dto.UserRequest;
import com.yondu.university.project_rohan.entity.Role;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.repository.RoleRepository;
import com.yondu.university.project_rohan.repository.UserRepository;
import com.yondu.university.project_rohan.util.PasswordGenerator;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userRepository
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String saveNewUser(UserRequest userRequest) {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        String tempPassword = PasswordGenerator.generateRandomPassword(9);
        String[] userRequestRoles = userRequest.getRole()
                .trim().replaceAll("\s+", "_").toUpperCase().split(",");

        for (String role : userRequestRoles) {
            if (role == null || role.isEmpty())
                continue;

            Role userRole = this.roleRepository.findByName(role)
                    .orElse(new Role(null, role));
            roles.add(userRole);
        }

        user.setEmail(userRequest.getEmail().trim());
        user.setFirstName(userRequest.getFirstName().trim());
        user.setLastName(userRequest.getLastName().trim());
        user.setRoles(roles);
        user.setPassword(this.passwordEncoder.encode(tempPassword));

        this.userRepository.save(user);
        return tempPassword;
    }

    public Page<User> findAllExceptCurrentUser(String currentUserEmail, String role, Pageable pageable) {
        if (role == null || role.trim().isEmpty())
            return this.userRepository.findByEmailIsNot(currentUserEmail, pageable);
        return this.userRepository.findWithRoleAndEmailIsNot(currentUserEmail, role.trim(), pageable);
    }

    public Boolean existsEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}

package com.yondu.university.project_rohan.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.UserRequest;
import com.yondu.university.project_rohan.entity.Role;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.service.RoleService;
import com.yondu.university.project_rohan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    /**
     * @param userService
     */
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserRequest> getUsers(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("email"));

        return this.userService.findAllExceptCurrentUser(currentUser, role, paging)
                .map(user -> convertToUserRequest(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String saveNewUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.saveNewUser(convertToUserEntity(userRequest));
    }

    @GetMapping(path = "{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRequest getUser(@PathVariable String email) {
        Optional<User> optionalUser = this.userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        UserRequest userRequest = convertToUserRequest(optionalUser.get());
        return userRequest;
    }

    @PatchMapping(path = "{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRequest deactivateUser(@PathVariable String email) {
        Optional<User> optionalUser = this.userService.deactivateUser(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        UserRequest userRequest = convertToUserRequest(optionalUser.get());
        return userRequest;
    }

    @GetMapping(path = "search")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRequest searchUser(@RequestParam String searchKey) {
        Optional<User> optionalUser = this.userService.searchUser(searchKey);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        UserRequest userRequest = convertToUserRequest(optionalUser.get());
        return userRequest;
    }

    private UserRequest convertToUserRequest(User user) {
        UserRequest userRequest = new UserRequest(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                String.join(",",
                        user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())),
                user.isActive());

        return userRequest;
    }

    private User convertToUserEntity(UserRequest userRequest) {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        String[] userRequestRoles = userRequest.getRole()
                .trim().replaceAll("\s+", " ").toUpperCase().split(",");

        for (String role : userRequestRoles) {
            if (role == null || role.isEmpty())
                continue;

            Role userRole = this.roleService.findByName(role)
                    .orElse(new Role(null, role));
            roles.add(userRole);
        }

        user.setEmail(userRequest.getEmail().trim());
        user.setFirstName(userRequest.getFirstName().trim());
        user.setLastName(userRequest.getLastName().trim());
        user.setRoles(roles);

        return user;
    }
}

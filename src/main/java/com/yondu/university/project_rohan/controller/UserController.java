package com.yondu.university.project_rohan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.UserRequest;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.UniqueConstraintException;
import com.yondu.university.project_rohan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("email"));
        return this.userService.findAllExceptCurrentUser(currentUser, role, paging);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String saveNewUser(@RequestBody @Valid UserRequest userRequest) {
        if (this.userService.existsEmail(userRequest.getEmail())) {
            throw new UniqueConstraintException("Email already exist.");
        }

        return userService.saveNewUser(userRequest);
    }
}

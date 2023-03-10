package com.yondu.university.project_rohan.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.CustomPage;
import com.yondu.university.project_rohan.dto.UserDto;
import com.yondu.university.project_rohan.entity.Role;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.service.RoleService;
import com.yondu.university.project_rohan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private static RoleService roleService;

    /**
     * @param userService
     */
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        UserController.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CustomPage<UserDto> getUsers(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("email"));

        Page<User> results = this.userService.findAllExceptCurrentUser(currentUser, role, paging);
        List<UserDto> userRequestList = results.getContent().stream()
                .map(user -> new UserDto(user)).collect(Collectors.toList());
        return new CustomPage<UserDto>(userRequestList, retrievedPage, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto saveNewUser(@RequestBody @Valid UserDto userRequest) {
        User newUser = userService.saveNewUser(convertToUserEntity(userRequest));
        return new UserDto(newUser).withPassword();
    }

    @GetMapping(path = "{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto getUser(@PathVariable String email) {
        User user = this.userService.findByEmail(email);
        return new UserDto(user).withStatus();
    }

    @PostMapping(path = "{email}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto deactivateUser(@PathVariable String email) {
        User user = this.userService.deactivateUser(email);
        return new UserDto(user).withStatus();
    }

    @GetMapping(path = "search")
    @PreAuthorize("hasRole('ADMIN')")
    public CustomPage<UserDto> searchUsers(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam String key,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("email"));

        Page<User> results = this.userService.searchUsers(key, currentUser, paging);
        List<UserDto> userRequestList = results.getContent().stream()
                .map(user -> new UserDto(user)).collect(Collectors.toList());
        return new CustomPage<UserDto>(userRequestList, retrievedPage, size);
    }

    public static final User convertToUserEntity(UserDto userRequest) {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        String[] userRequestRoles = userRequest.getRole()
                .trim().replaceAll("\s+", " ").toUpperCase().split(",");

        for (String role : userRequestRoles) {
            if (role == null || role.isBlank())
                continue;

            Role userRole = roleService.findByName(role.trim())
                    .orElse(new Role(null, role.trim().toUpperCase()));
            roles.add(userRole);
        }

        user.setEmail(userRequest.getEmail().trim());
        user.setFirstName(userRequest.getFirstName().trim());
        user.setLastName(userRequest.getLastName().trim());
        user.setRoles(roles);

        return user;
    }
}

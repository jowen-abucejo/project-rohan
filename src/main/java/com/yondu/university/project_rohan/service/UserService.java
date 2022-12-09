package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.repository.UserRepository;
import com.yondu.university.project_rohan.util.PasswordGenerator;

import jakarta.validation.Valid;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ROLE_STUDENT = "STUDENT";

    /**
     * @param userRepository
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveNewUser(@Valid User user) {
        String tempPassword = PasswordGenerator.generateRandomPassword(9);
        user.setPassword(this.passwordEncoder.encode(tempPassword));
        return this.userRepository.save(user);
        // return tempPassword;
    }

    public Page<User> findAllExceptCurrentUser(String currentUserEmail, String role, Pageable pageable) {
        if (role == null || role.trim().isEmpty())
            return this.userRepository.findByEmailIsNot(currentUserEmail, pageable);
        return this.userRepository.findByRoleAndEmailIsNot(currentUserEmail, role.trim(), pageable);
    }

    public Boolean isEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public Page<User> searchUsers(String searchKey, String currentUser, Pageable pageable) {
        return this.userRepository.findByEmailOrFirstNameOrLastName(searchKey, currentUser, pageable);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> deactivateUser(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return optionalUser;
        }

        User user = optionalUser.get();
        user.setActive(false);

        return Optional.of(this.userRepository.save(user));
    }

    public Optional<User> findStudentByEmailAndIsActive(String email) {
        return this.userRepository.findByRoleAndEmailAndStatus(ROLE_STUDENT, email, true);
    }

    public Optional<User> findStudentByCourseClassAndEmail(String courseCode, int batch, String email) {
        return this.userRepository.findByCourseClassAndEmail(courseCode, batch, email);
    }

    public Page<User> findStudentsBySMECourseClass(String smeEmail, String courseCode, int batch, Pageable pageable) {
        return this.userRepository.findBySMECourseClassAndEmail(smeEmail, courseCode, batch, pageable);
    }
}

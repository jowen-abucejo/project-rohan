package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.repository.UserRepository;
import com.yondu.university.project_rohan.util.PasswordGenerator;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final String ROLE_STUDENT = "STUDENT";

    /**
     * @param userRepository
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User saveNewUser(User user) {
        String tempPassword = PasswordGenerator.generateRandomPassword(9);
        user.setPassword(this.passwordEncoder.encode(tempPassword));

        String message = String.format("Your account password is %s", tempPassword);
        if (!emailService.sendPassword(user.getEmail(), "Project Rohan Account Created", message)) {
            // Log message
        }
        return this.userRepository.save(user);
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

    public User findByEmail(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        return optionalUser.get();

    }

    public User deactivateUser(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        User user = optionalUser.get();
        user.setActive(false);

        return this.userRepository.save(user);
    }

    public User findStudentByEmailAndIsActive(String email) {
        Optional<User> optionalStudent = this.userRepository.findByRoleAndEmailAndStatus(ROLE_STUDENT, email, true);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email don't match to any active student.");
        }

        return optionalStudent.get();
    }

    public User findStudentByCourseClassAndEmail(String courseCode, int batch, String email) {
        Optional<User> optionalStudent = this.userRepository.findByCourseClassAndEmail(courseCode, batch, email);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email doesn't match to any enrolled student.");
        }
        return optionalStudent.get();
    }

    public Page<User> findStudentsBySMECourseClass(String smeEmail, String courseCode, int batch, Pageable pageable) {
        return this.userRepository.findBySMECourseClassAndEmail(smeEmail, courseCode, batch, pageable);
    }
}

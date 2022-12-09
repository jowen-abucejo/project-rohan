package com.yondu.university.project_rohan.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByEmail(String email);

        Optional<User> findByEmailAndIsActiveIsTrue(String email);

        Page<User> findByEmailIsNot(String email, Pageable pageable);

        Boolean existsByEmail(String email);

        @Query(value = "SELECT u.* FROM user u INNER JOIN user_role ur ON u.id=ur.user_id " +
                        "INNER JOIN role r ON r.id=ur.role_id WHERE u.email!=:email AND r.name=:role ", nativeQuery = true)
        Page<User> findByRoleAndEmailIsNot(String role, String email, Pageable pageable);

        @Query(value = "SELECT * FROM user WHERE (email=:search OR first_name=:search OR last_name=:search) AND email!=:email", nativeQuery = true)
        Page<User> findByEmailOrFirstNameOrLastName(String search, String email, Pageable pageable);

        @Query(value = "SELECT u.* FROM user u INNER JOIN user_role ur ON u.id=ur.user_id INNER JOIN role r ON r.id=ur.role_id "
                        + "WHERE u.email=:email AND r.name=:role AND is_active=:status LIMIT 1", nativeQuery = true)
        Optional<User> findByRoleAndEmailAndStatus(String role, String email, boolean status);

        @Query(value = "SELECT u.* FROM user u INNER JOIN student_class sc ON u.id=sc.user_id INNER JOIN class c ON c.id=sc.class_id "
                        + "WHERE c.course_code=:code AND c.batch_number=:batch AND u.email=:email LIMIT 1", nativeQuery = true)
        Optional<User> findByCourseClassAndEmail(String code, int batch, String email);
}

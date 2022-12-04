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
            "INNER JOIN role r ON r.id=ur.role_id WHERE u.email!=? AND r.name=? ", nativeQuery = true)
    Page<User> findWithRoleAndEmailIsNot(String email, String role, Pageable page);

    @Query(value = "SELECT * FROM user WHERE email=:search OR first_name=:search OR last_name=:search LIMIT 1", nativeQuery = true)
    Optional<User> findByEmailOrFirstNameOrLastName(String search);

}

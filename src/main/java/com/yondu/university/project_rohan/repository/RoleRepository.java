package com.yondu.university.project_rohan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

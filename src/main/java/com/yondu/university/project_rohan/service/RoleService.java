package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Role;
import com.yondu.university.project_rohan.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * @param roleRepository
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}

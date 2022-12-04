package com.yondu.university.project_rohan.entity;

import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(nullable = false, unique = true, length = 64)
    private String name;

    /**
     * 
     */
    public Role() {
    }

    /**
     * @param id
     * @param name
     */
    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    @JsonIgnore
    public Integer getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

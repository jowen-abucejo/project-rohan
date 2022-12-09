package com.yondu.university.project_rohan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

}

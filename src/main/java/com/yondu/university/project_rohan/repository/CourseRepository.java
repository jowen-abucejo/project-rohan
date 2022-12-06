package com.yondu.university.project_rohan.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);

    Page<Course> findAll(Pageable pageable);

    Boolean existsByCode(String code);

    @Query(value = "SELECT * FROM course WHERE code=:search OR title=:search OR description=:search", nativeQuery = true)
    Page<Course> findByCodeOrTitleOrDescription(String search, Pageable pageable);
}

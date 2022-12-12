package com.yondu.university.project_rohan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    @Query(value = "SELECT a.* FROM activity a WHERE a.id=:id AND a.type=:type AND a.class_id=:classId LIMIT 1", nativeQuery = true)
    Optional<Activity> findByIdAndClassIdAndType(Integer id, Integer classId, String type);

    @Query(value = "SELECT a.* FROM activity a INNER JOIN class c ON a.class_id=c.id "
            + "WHERE a.id=:id AND a.type=:type AND c.sme_email=:email LIMIT 1", nativeQuery = true)
    Optional<Activity> findByIdAndTypeAndSmeEmail(Integer id, String type, String email);

    @Query(value = "SELECT a.* FROM activity a INNER JOIN class c ON a.class_id=c.id WHERE a.type=:type AND "
            + "c.course_code=:code AND c.batch_number=:batch AND c.sme_email=:email LIMIT 1", nativeQuery = true)
    Optional<Activity> findByClassAndTypeAndSmeEmail(String type, String code, Integer batch, String email);
}

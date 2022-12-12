package com.yondu.university.project_rohan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yondu.university.project_rohan.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM score WHERE activity_id=:id AND student_email=:email) "
            + "THEN 'true' ELSE 'FALSE' END", nativeQuery = true)
    boolean isExistsByActivityAndStudent(Integer id, String email);
}

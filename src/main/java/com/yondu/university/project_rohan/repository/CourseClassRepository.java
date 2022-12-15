package com.yondu.university.project_rohan.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yondu.university.project_rohan.entity.CourseClass;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Integer> {

        @Query(value = "SELECT * FROM class WHERE sme_email=:email", nativeQuery = true)
        Page<CourseClass> findBySMEEmail(String email, Pageable page);

        @Query(value = "SELECT COALESCE(MAX(batch_number), 0) FROM class WHERE course_code=:code", nativeQuery = true)
        Integer findMaxBatchByCourseCode(String code);

        @Query(value = "SELECT c1.* FROM class c1 INNER JOIN course c2 ON c1.course_code=c2.code "
                        + "WHERE c1.sme_email=:email AND (c1.start_date=:key OR c1.end_date=:key OR c2.code=:key "
                        + "OR c2.title=:key OR c2.description=:key )", nativeQuery = true)
        Page<CourseClass> findByCourseOrStartDateOrEndDate(String email, String key, Pageable paging);

        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Transactional
        @Query(value = "UPDATE class SET is_active=0,updated_by=:email,updated_at=NOW() WHERE sme_email=:email AND course_code=:code AND batch_number=:batch "
                        + "AND (CURDATE() > end_date OR CURDATE() < start_date) LIMIT 1", nativeQuery = true)
        void deactivateBySMEAndCourseCodeAndBatchAndNotOngoing(String email, String code, int batch);

        @Query(value = "SELECT * FROM class WHERE sme_email=:email AND course_code=:code AND batch_number=:batch "
                        + "AND (CURDATE() > end_date OR CURDATE() < start_date) LIMIT 1", nativeQuery = true)
        Optional<CourseClass> findBySMEAndCourseCodeAndBatchAndNotOngoing(String email, String code, int batch);

        @Query(value = "SELECT * FROM class WHERE sme_email=:email AND course_code=:code AND batch_number=:batch LIMIT 1", nativeQuery = true)
        Optional<CourseClass> findBySMEAndCourseCodeAndBatch(String email, String code, int batch);

        @Query(value = "SELECT * FROM class WHERE sme_email=:email AND course_code=:code AND batch_number=:batch "
                        + "AND CURDATE() < start_date LIMIT 1", nativeQuery = true)
        Optional<CourseClass> findBySMEAndCourseCodeAndBatchAndOpen(String email, String code, int batch);

        @Query(value = "SELECT c.* FROM class c INNER JOIN student_class sc ON c.id=sc.class_id INNER JOIN user u "
                        + "ON sc.user_id=u.id WHERE u.email=:email", nativeQuery = true)
        Page<CourseClass> findAllByStudentEmail(String email, Pageable paging);

        @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM user u INNER JOIN student_class sc ON u.id=sc.user_id INNER JOIN class c ON c.id=sc.class_id "
                        + "WHERE c.course_code=:code AND c.batch_number=:batch AND u.email=:email) "
                        + "THEN 'true' ELSE 'FALSE' END", nativeQuery = true)
        boolean isStudentEnrolledInClass(String email, String code, Integer batch);

        @Query(value = "SELECT * FROM class WHERE course_code=:code AND batch_number=:batch AND CURDATE() > start_date LIMIT 1", nativeQuery = true)
        Optional<CourseClass> findByCourseAndBatchAndClose(String code, int batch);
}

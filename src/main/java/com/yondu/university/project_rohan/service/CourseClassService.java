package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.repository.CourseClassRepository;

@Service
public class CourseClassService {
    private final CourseClassRepository classRepository;

    /**
     * @param classRepository
     */
    public CourseClassService(CourseClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Page<CourseClass> findAllBySMEEmail(String email, Pageable paging) {
        return this.classRepository.findBySMEEmail(email, paging);
    }

    public CourseClass saveNewCourseClass(CourseClass courseClass) {
        int batchNumber = this.classRepository.findMaxBatchByCourseCode(courseClass.getCourse().getCode()) + 1;
        courseClass.setBatchNumber(batchNumber);
        return this.classRepository.save(courseClass);
    }

    public Optional<CourseClass> findBySMEAndCourseAndBatch(String currentUser, String courseCode, int batch) {
        return this.classRepository.findBySMEAndCourseCodeAndBatch(currentUser, courseCode, batch);
    }

    public Page<CourseClass> searchSMEClasses(String currentUser, String key, Pageable paging) {
        return this.classRepository.findByCourseOrStartDateOrEndDate(currentUser, key, paging);
    }

    public Optional<CourseClass> deactivateCourseClass(String currentUser, String courseCode, int batch) {
        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndNotOngoing(currentUser, courseCode, batch);

        if (optionalCourseClass.isEmpty()) {
            return optionalCourseClass;
        }

        CourseClass course = optionalCourseClass.get();
        course.setActive(false);

        return Optional.of(this.classRepository.save(course));
    }

    public Optional<CourseClass> enrollStudentToSMECourseClass(User student, String currentUser, String code,
            int batch) {
        // find the class that is not already ended
        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndOpen(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            return optionalCourseClass;
        }

        CourseClass courseClass = optionalCourseClass.get();
        courseClass.getStudents().add(student);

        return Optional.of(this.classRepository.save(courseClass));
    }

    public Optional<CourseClass> unEnrollStudentFromSMECourseClass(User student, String currentUser, String code,
            int batch) {
        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndNotOngoing(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            return optionalCourseClass;
        }

        CourseClass courseClass = optionalCourseClass.get();
        courseClass.getStudents().remove(student);

        return Optional.of(this.classRepository.save(courseClass));
    }
}

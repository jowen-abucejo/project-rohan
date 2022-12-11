package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.repository.CourseClassRepository;

@Service
public class CourseClassService {
    private final CourseClassRepository classRepository;
    private final UserService userService;

    /**
     * @param classRepository
     */
    public CourseClassService(CourseClassRepository classRepository, UserService userService) {
        this.classRepository = classRepository;
        this.userService = userService;
    }

    public Page<CourseClass> findAllBySMEEmail(String email, Pageable paging) {
        return this.classRepository.findBySMEEmail(email, paging);
    }

    public CourseClass saveNewCourseClass(CourseClass courseClass) {
        int batchNumber = this.classRepository.findMaxBatchByCourseCode(courseClass.getCourse().getCode()) + 1;
        courseClass.setBatchNumber(batchNumber);
        return this.classRepository.save(courseClass);
    }

    public CourseClass findBySMEAndCourseAndBatch(String currentUser, String courseCode, int batch) {
        Optional<CourseClass> optionalCourseClass = this.classRepository.findBySMEAndCourseCodeAndBatch(currentUser,
                courseCode, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class not found.");
        }
        return optionalCourseClass.get();
    }

    public Page<CourseClass> searchSMEClasses(String currentUser, String key, Pageable paging) {
        return this.classRepository.findByCourseOrStartDateOrEndDate(currentUser, key, paging);
    }

    public CourseClass deactivateCourseClass(String currentUser, String courseCode, int batch) {
        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndNotOngoing(currentUser, courseCode, batch);

        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Non-ongoing class not found.");
        }

        CourseClass course = optionalCourseClass.get();
        course.setActive(false);

        return this.classRepository.save(course);
    }

    public CourseClass enrollStudentToSMECourseClass(String studentEmail, String currentUser, String code,
            int batch) {
        User student = userService.findStudentByEmailAndIsActive(studentEmail);

        // find the class that is not already ended
        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndOpen(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Class with the given course and batch not found or enrollment closed.");
        }

        CourseClass courseClass = optionalCourseClass.get();
        courseClass.getStudents().add(student);

        return this.classRepository.save(courseClass);
    }

    public CourseClass unEnrollStudentFromSMECourseClass(String studentEmail, String currentUser, String code,
            int batch) {

        User student = userService.findStudentByCourseClassAndEmail(code, batch, studentEmail);

        Optional<CourseClass> optionalCourseClass = this.classRepository
                .findBySMEAndCourseCodeAndBatchAndNotOngoing(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Non-ongoing class with the given course and batch not found.");
        }

        CourseClass courseClass = optionalCourseClass.get();
        courseClass.getStudents().remove(student);

        return this.classRepository.save(courseClass);
    }

    public Page<CourseClass> findAllByStudentEmail(String email, Pageable paging) {
        return this.classRepository.findAllByStudentEmail(email, paging);
    }
}

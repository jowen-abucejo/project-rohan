package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * @param courseRepository
     */
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveNewCourse(Course course) {
        return this.courseRepository.save(course);
    }

    public Page<Course> findAll(Pageable pageable) {
        return this.courseRepository.findAll(pageable);
    }

    public Boolean isCodeExist(String code) {
        return this.courseRepository.existsByCode(code);
    }

    public Page<Course> searchCourses(String searchKey, Pageable pageable) {
        return this.courseRepository.findByCodeOrTitleOrDescription(searchKey, pageable);
    }

    public Optional<Course> findByCode(String code) {
        return this.courseRepository.findByCode(code);
    }

    public Optional<Course> deactivateCourse(String code) {
        Optional<Course> optionalCourse = this.courseRepository.findByCode(code);
        if (optionalCourse.isEmpty()) {
            return optionalCourse;
        }

        Course course = optionalCourse.get();
        course.setActive(false);

        return Optional.of(this.courseRepository.save(course));
    }

}

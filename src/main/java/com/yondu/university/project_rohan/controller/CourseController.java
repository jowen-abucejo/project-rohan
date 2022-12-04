package com.yondu.university.project_rohan.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.CourseRequest;
import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    /**
     * @param courseService
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public Page<CourseRequest> getCourses(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("code"));

        return this.courseService.findAll(paging).map(course -> convertToCourseRequest(course));

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest saveNewCourse(@RequestBody @Valid CourseRequest courseRequest) {
        Course newCourse = courseService.saveNewCourse(convertToCourseEntity(courseRequest));
        return convertToCourseRequest(newCourse);
    }

    @GetMapping(path = "{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest getCourse(@PathVariable String code) {
        Optional<Course> optionalCourse = this.courseService.findByCode(code.trim());
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course not found.");
        }

        return convertToCourseRequest(optionalCourse.get());
    }

    @PatchMapping(path = "{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest deactivateCourse(@PathVariable String code) {
        Optional<Course> optionalCourse = this.courseService.deactivateCourse(code.trim());
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course not found.");
        }

        return convertToCourseRequest(optionalCourse.get());
    }

    @GetMapping(path = "search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest searchUser(@RequestParam String searchKey) {
        Optional<Course> optionalCourse = this.courseService.searchCourse(searchKey.trim());
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course not found.");
        }

        return convertToCourseRequest(optionalCourse.get());
    }

    private CourseRequest convertToCourseRequest(Course course) {
        CourseRequest courseRequest = new CourseRequest(
                course.getCode().trim(),
                course.getTitle().trim(),
                course.getDescription().trim(),
                course.isActive());

        return courseRequest;
    }

    private Course convertToCourseEntity(CourseRequest courseRequest) {
        Course course = new Course();
        course.setCode(courseRequest.getCode());
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());

        return course;
    }

}

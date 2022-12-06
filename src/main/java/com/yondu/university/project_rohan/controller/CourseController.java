package com.yondu.university.project_rohan.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.CourseRequest;
import com.yondu.university.project_rohan.dto.CustomPage;
import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("courses")
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
    public CustomPage<CourseRequest> getCourses(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page - 1);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("code"));

        Page<Course> results = this.courseService.findAll(paging);
        List<CourseRequest> courseRequestList = results.getContent().stream()
                .map(course -> convertToCourseRequest(course, true, false)).collect(Collectors.toList());

        return new CustomPage<CourseRequest>(courseRequestList, retrievedPage, size);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest saveNewCourse(@RequestBody @Valid CourseRequest courseRequest) {
        Course newCourse = courseService.saveNewCourse(convertToCourseEntity(courseRequest));
        return convertToCourseRequest(newCourse, true, false);
    }

    @GetMapping(path = "{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest getCourse(@PathVariable String code) {
        Optional<Course> optionalCourse = this.courseService.findByCode(code.trim());
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course not found.");
        }

        return convertToCourseRequest(optionalCourse.get(), true, true);
    }

    @PostMapping(path = "{code}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseRequest deactivateCourse(@PathVariable String code) {
        Optional<Course> optionalCourse = this.courseService.deactivateCourse(code.trim());
        if (optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course not found.");
        }

        return convertToCourseRequest(optionalCourse.get(), true, true);
    }

    @GetMapping(path = "search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CustomPage<CourseRequest> searchCourses(@RequestParam String key, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page - 1);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("code"));

        Page<Course> results = this.courseService.searchCourses(key, paging);
        List<CourseRequest> courseRequestList = results.getContent().stream()
                .map(course -> convertToCourseRequest(course, true, false)).collect(Collectors.toList());

        return new CustomPage<CourseRequest>(courseRequestList, retrievedPage, size);
    }

    private CourseRequest convertToCourseRequest(Course course, boolean includeId, boolean includeStatus) {
        CourseRequest courseRequest = new CourseRequest(course.getCode(), course.getTitle(),
                course.getDescription());

        if (includeId) {
            courseRequest.setId(course.getId() + "");
        }

        if (includeStatus) {
            if (course.isActive()) {
                courseRequest.setStatus("Active");
            } else {
                courseRequest.setStatus("Inactive");
            }
        }
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

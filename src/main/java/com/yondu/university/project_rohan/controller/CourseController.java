package com.yondu.university.project_rohan.controller;

import java.util.List;
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

import com.yondu.university.project_rohan.dto.CourseDto;
import com.yondu.university.project_rohan.dto.CustomPage;
import com.yondu.university.project_rohan.entity.Course;
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
    public CustomPage<CourseDto> getCourses(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("code"));

        Page<Course> results = this.courseService.findAll(paging);
        List<CourseDto> courseRequestList = results.getContent().stream()
                .map(course -> new CourseDto(course).withId()).collect(Collectors.toList());

        return new CustomPage<CourseDto>(courseRequestList, retrievedPage, size);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseDto saveNewCourse(@RequestBody @Valid CourseDto courseRequest) {
        Course newCourse = courseService.saveNewCourse(convertToCourseEntity(courseRequest));
        return new CourseDto(newCourse).withId();
    }

    @GetMapping(path = "{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseDto getCourse(@PathVariable String code) {
        Course course = this.courseService.findByCode(code);
        return new CourseDto(course).withId().withStatus();
    }

    @PostMapping(path = "{code}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CourseDto deactivateCourse(@PathVariable String code) {
        Course course = this.courseService.deactivateCourse(code);
        return new CourseDto(course).withId().withStatus();
    }

    @GetMapping(path = "search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CustomPage<CourseDto> searchCourses(@RequestParam String key, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page - 1);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("code"));

        Page<Course> results = this.courseService.searchCourses(key, paging);
        List<CourseDto> courseRequestList = results.getContent().stream()
                .map(course -> new CourseDto(course).withId()).collect(Collectors.toList());

        return new CustomPage<CourseDto>(courseRequestList, retrievedPage, size);
    }

    public static final Course convertToCourseEntity(CourseDto courseRequest) {
        Course course = new Course();
        course.setCode(courseRequest.getCode());
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());

        return course;
    }

}

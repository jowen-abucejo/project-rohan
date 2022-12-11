package com.yondu.university.project_rohan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.CourseClassDto;
import com.yondu.university.project_rohan.dto.CustomPage;
import com.yondu.university.project_rohan.dto.EnrollmentRequest;
import com.yondu.university.project_rohan.dto.UserDto;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.service.CourseClassService;
import com.yondu.university.project_rohan.service.CourseService;
import com.yondu.university.project_rohan.service.UserService;

import jakarta.validation.Valid;

@RestController
public class CourseClassController {
    private final CourseClassService classService;
    private static CourseService courseService;
    private static UserService userService;

    /**
     * @param classService
     */
    public CourseClassController(CourseClassService classService, CourseService courseService,
            UserService userService) {
        this.classService = classService;
        CourseClassController.courseService = courseService;
        CourseClassController.userService = userService;
    }

    @GetMapping(path = "classes")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CustomPage<CourseClassDto> getClasses(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("id"));

        Page<CourseClass> results = this.classService.findAllBySMEEmail(currentUser, paging);
        List<CourseClassDto> courseClassDtoList = results.getContent().stream()
                .map(courseClass -> new CourseClassDto(courseClass).withStatus().withCourse(true).withSME())
                .collect(Collectors.toList());
        return new CustomPage<CourseClassDto>(courseClassDtoList, retrievedPage, size);
    }

    @PostMapping(path = "classes")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto saveNewCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestBody @Valid CourseClassDto courseClassDto) {
        CourseClass newClass = this.classService
                .saveNewCourseClass(convertToCourseClassEntity(currentUser, courseClassDto));

        return new CourseClassDto(newClass).withStatus().withCourse(false).withSME();
    }

    @GetMapping(path = "classes/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUBJECT_MATTER_EXPERT')")
    public CustomPage<CourseClassDto> searchCourseClasses(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @RequestParam String key, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("id"));

        Page<CourseClass> results = this.classService.searchSMEClasses(currentUser, key, paging);
        List<CourseClassDto> courseClassDtoList = results.getContent().stream()
                .map(courseClass -> new CourseClassDto(courseClass).withStatus().withCourse(false).withSME())
                .collect(Collectors.toList());
        return new CustomPage<CourseClassDto>(courseClassDtoList, retrievedPage, size);
    }

    @GetMapping(path = "courses/{code}/classes/{batch}")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto getCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {
        CourseClass courseClass = this.classService.findBySMEAndCourseAndBatch(currentUser, code,
                batch);
        return new CourseClassDto(courseClass).withStatus().withCourse(false).withSME();
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/deactivate")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto deactivateCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {

        CourseClass courseClass = this.classService.deactivateCourseClass(currentUser, code, batch);
        return new CourseClassDto(courseClass).withStatus().withCourse(false).withSME();
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/enroll")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto enrollStudentToCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid EnrollmentRequest enrollmentRequest) {

        CourseClass courseClass = this.classService
                .enrollStudentToSMECourseClass(enrollmentRequest.getEmail(), currentUser, code, batch);

        return new CourseClassDto(courseClass).withStatus().withCourse(false).withSME().withStudents();
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/unenroll")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto unEnrollStudentFromCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid EnrollmentRequest enrollmentRequest) {

        CourseClass courseClass = this.classService
                .unEnrollStudentFromSMECourseClass(enrollmentRequest.getEmail(), currentUser, code, batch);

        return new CourseClassDto(courseClass).withStatus().withCourse(false).withSME().withStudents();
    }

    @GetMapping(path = "courses/{code}/classes/{batch}/students")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CustomPage<UserDto> getCourseClassStudents(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int retrievedPage = Math.max(1, page);
        Pageable paging = PageRequest.of(retrievedPage - 1, size, Sort.by("id"));

        Page<User> students = userService.findStudentsBySMECourseClass(currentUser, code, batch, paging);
        List<UserDto> studentDtoList = students.getContent().stream()
                .map(student -> new UserDto(student).withStatus()).collect(Collectors.toList());
        return new CustomPage<UserDto>(studentDtoList, retrievedPage, size);
    }

    public static final CourseClass convertToCourseClassEntity(String sme_email, CourseClassDto courseClassDto) {
        CourseClass courseClass = new CourseClass();
        courseClass.setQuizPercentage(courseClassDto.getQuizPercentage());
        courseClass.setAttendancePercentage(courseClassDto.getAttendancePercentage());
        courseClass.setExercisePercentage(courseClassDto.getExercisePercentage());
        courseClass.setProjectPercentage(courseClassDto.getProjectPercentage());
        courseClass.setStartDate(courseClassDto.getStartDate());
        courseClass.setEndDate(courseClassDto.getEndDate());

        courseClass.setCourse(courseService.findByCode(courseClassDto.getCourseCode()));
        courseClass.setSubjectMatterExpert(userService.findByEmail(sme_email));

        return courseClass;
    }
}

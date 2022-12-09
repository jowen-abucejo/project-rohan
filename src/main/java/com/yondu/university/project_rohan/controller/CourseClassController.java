package com.yondu.university.project_rohan.controller;

import java.util.List;
import java.util.Optional;
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
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
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
                .map(courseClass -> convertToCourseClassDTO(courseClass, true, true, true, false, true))
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

        courseClassDto = convertToCourseClassDTO(newClass, true, true, true, false, false);

        return courseClassDto;
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
                .map(courseClass -> convertToCourseClassDTO(courseClass, true, true, true, false, false))
                .collect(Collectors.toList());
        return new CustomPage<CourseClassDto>(courseClassDtoList, retrievedPage, size);
    }

    @GetMapping(path = "courses/{code}/classes/{batch}")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto getCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {
        Optional<CourseClass> optionalCourseClass = this.classService.findBySMEAndCourseAndBatch(currentUser, code,
                batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class not found.");
        }

        return convertToCourseClassDTO(optionalCourseClass.get(), true, true, true, false, false);
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/deactivate")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto deactivateCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {
        Optional<CourseClass> optionalCourseClass = this.classService.deactivateCourseClass(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Non-ongoing class not found.");
        }

        return convertToCourseClassDTO(optionalCourseClass.get(), true, true, true, false, false);
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/enroll")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto enrollStudentToCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid EnrollmentRequest enrollmentRequest) {

        Optional<User> optionalStudent = userService.findStudentByEmailAndIsActive(enrollmentRequest.getEmail());
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email don't match to any active student.");
        }

        Optional<CourseClass> optionalCourseClass = this.classService
                .enrollStudentToSMECourseClass(optionalStudent.get(), currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Class with the given course and batch not found or enrollment closed.");
        }

        return convertToCourseClassDTO(optionalCourseClass.get(), true, true, true, true, false);
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/unenroll")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto unEnrollStudentFromCourseClass(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid EnrollmentRequest enrollmentRequest) {

        Optional<User> optionalStudent = userService.findStudentByCourseClassAndEmail(code, batch,
                enrollmentRequest.getEmail());
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Email don't match to any student enrolled in given class or class doesn't exist.");
        }

        Optional<CourseClass> optionalCourseClass = this.classService
                .unEnrollStudentFromSMECourseClass(optionalStudent.get(), currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Non-ongoing class with the given course and batch not found.");
        }

        return convertToCourseClassDTO(optionalCourseClass.get(), true, true, true, true, false);
    }

    @GetMapping(path = "courses/{code}/classes/{batch}/students")
    @PostMapping(path = "courses/{code}/classes/{batch}/unenroll")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public CourseClassDto getCourseClassStudents(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {
        Optional<CourseClass> optionalCourseClass = this.classService.findBySMEAndCourseAndBatch(currentUser, code,
                batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class not found.");
        }

        return convertToCourseClassDTO(optionalCourseClass.get(), true, true, true, true, false);
    }

    public static final CourseClassDto convertToCourseClassDTO(CourseClass courseClass, boolean includeStatus,
            boolean includeCourse, boolean includeSME, boolean includeStudents, boolean includeCourseId) {

        CourseClassDto courseClassDto = new CourseClassDto();
        courseClassDto.setBatch(courseClass.getBatchNumber());
        courseClassDto.setQuizPercentage(courseClass.getQuizPercentage());
        courseClassDto.setExercisePercentage(courseClass.getExercisePercentage());
        courseClassDto.setProjectPercentage(courseClass.getProjectPercentage());
        courseClassDto.setAttendancePercentage(courseClass.getAttendancePercentage());
        courseClassDto.setStartDate(courseClass.getStartDate());
        courseClassDto.setEndDate(courseClass.getEndDate());

        if (includeStatus) {
            if (courseClass.isActive()) {
                courseClassDto.setStatus("Active");
            } else {
                courseClassDto.setStatus("Inactive");
            }
        }

        if (includeCourse) {
            courseClassDto.setCourseCode(null);
            courseClassDto
                    .setCourse(CourseController.convertToCourseDTO(courseClass.getCourse(), includeCourseId, true));
        } else {
            courseClassDto.setCourseCode(courseClass.getCourse().getCode());
        }

        if (includeSME) {
            courseClassDto.setSme(UserController.convertToUserDTO(courseClass.getSubjectMatterExpert(),
                    true, false));
        }

        if (includeStudents) {
            List<UserDto> students = courseClass.getStudents().stream().map(
                    student -> new UserDto(student.getEmail(), student.getFirstName(), student.getLastName(), null))
                    .collect(Collectors.toList());
            courseClassDto.setStudents(students);
        }

        return courseClassDto;
    }

    public static final CourseClass convertToCourseClassEntity(String sme_email, CourseClassDto courseClassDto) {
        CourseClass courseClass = new CourseClass();
        courseClass.setQuizPercentage(courseClassDto.getQuizPercentage());
        courseClass.setAttendancePercentage(courseClassDto.getAttendancePercentage());
        courseClass.setExercisePercentage(courseClassDto.getExercisePercentage());
        courseClass.setProjectPercentage(courseClassDto.getProjectPercentage());
        courseClass.setStartDate(courseClassDto.getStartDate());
        courseClass.setEndDate(courseClassDto.getEndDate());

        courseClass.setCourse(courseService.findByCode(courseClassDto.getCourseCode()).get());
        courseClass.setSubjectMatterExpert(userService.findByEmail(sme_email).get());

        return courseClass;
    }
}

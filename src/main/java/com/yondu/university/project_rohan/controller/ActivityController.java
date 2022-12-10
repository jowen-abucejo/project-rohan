package com.yondu.university.project_rohan.controller;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.ActivityDto;
import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.service.ActivityService;
import com.yondu.university.project_rohan.service.CourseClassService;

import jakarta.validation.Valid;

@RestController
public class ActivityController {
    private final ActivityService activityService;
    private static CourseClassService classService;

    /**
     * @param activityService
     */
    public ActivityController(ActivityService activityService, CourseClassService classService) {
        this.activityService = activityService;
        ActivityController.classService = classService;
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/quizzes")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto saveNewQuiz(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid ActivityDto quizDto) {

        Optional<CourseClass> optionalCourseClass = classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class with the given course and batch not found.");
        }
        Activity quiz = convertToActivityEntity(quizDto);
        quiz.setCourseClass(optionalCourseClass.get());

        return convertToActivityDTO(this.activityService.saveNewQuiz(quiz));
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/exercises")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto saveNewExercise(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid ActivityDto exerciseDto) {

        Optional<CourseClass> optionalCourseClass = classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class with the given course and batch not found.");
        }
        Activity exercise = convertToActivityEntity(exerciseDto);
        exercise.setCourseClass(optionalCourseClass.get());

        return convertToActivityDTO(this.activityService.saveNewExercise(exercise));
    }

    // @DeleteMapping(path = "courses/{code}/classes/{batch}/quizzes/{id}/delete")
    // @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    // public ActivityDto deleteQuiz(
    // @CurrentSecurityContext(expression = "authentication.getName()") String
    // currentUser,
    // @PathVariable String code, @PathVariable int batch,
    // @PathVariable int id) {

    // Optional<CourseClass> optionalCourseClass =
    // classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
    // if (optionalCourseClass.isEmpty()) {
    // throw new ResourceNotFoundException("Class with the given course and batch
    // not found.");
    // }

    // Optional<Activity> optionalQuiz = this.activityService.deleteQuiz(id,
    // optionalCourseClass.get().getId());

    // if (optionalQuiz.isEmpty()) {
    // throw new ResourceNotFoundException("Quiz not found.");
    // }

    // Activity quiz = optionalQuiz.get();
    // if (!quiz.getScores().isEmpty()) {
    // throw new ResourceNotFoundException("Quiz cannot be deleted.");
    // }

    // return convertToActivityDTO(quiz);
    // }

    // @DeleteMapping(path = "courses/{code}/classes/{batch}/exercises/{id}/delete")
    // @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    // public ActivityDto deleteExercise(
    // @CurrentSecurityContext(expression = "authentication.getName()") String
    // currentUser,
    // @PathVariable String code, @PathVariable int batch,
    // @PathVariable int id) {

    // Optional<CourseClass> optionalCourseClass =
    // classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
    // if (optionalCourseClass.isEmpty()) {
    // throw new ResourceNotFoundException("Class with the given course and batch
    // not found.");
    // }

    // Optional<Activity> optionalExercise = this.activityService.deleteExercise(id,
    // optionalCourseClass.get().getId());

    // if (optionalExercise.isEmpty()) {
    // throw new ResourceNotFoundException("Exercise not found.");
    // }

    // Activity exercise = optionalExercise.get();
    // if (!exercise.getScores().isEmpty()) {
    // throw new ResourceNotFoundException("Exercise cannot be deleted.");
    // }

    // return convertToActivityDTO(exercise);
    // }

    @DeleteMapping(path = "quizzes/{id}/delete")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto deleteQuizById(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser, @PathVariable int id) {
        Optional<Activity> optionalActivity = this.activityService.deleteQuizByIdAndSMEEmail(id, currentUser);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Quiz not found.");
        }

        Activity quiz = optionalActivity.get();
        if (!quiz.getScores().isEmpty()) {
            throw new ResourceNotFoundException("Quiz cannot be deleted.");
        }

        return convertToActivityDTO(quiz);
    }

    @DeleteMapping(path = "exercises/{id}/delete")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto deleteExerciseById(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser, @PathVariable int id) {
        Optional<Activity> optionalActivity = this.activityService.deleteExerciseByIdAndSMEEmail(id, currentUser);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Exercise not found.");
        }

        Activity exercise = optionalActivity.get();
        if (!exercise.getScores().isEmpty()) {
            throw new ResourceNotFoundException("Exercise cannot be deleted.");
        }

        return convertToActivityDTO(exercise);
    }

    public static final ActivityDto convertToActivityDTO(Activity activity) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activity.getId() + "");
        activityDto.setCourseCode(activity.getCourseClass().getCourse().getCode());
        activityDto.setBatch(activity.getCourseClass().getBatchNumber());
        activityDto.setTitle(activity.getTitle());
        activityDto.setMaxScore(activity.getMaxScore());
        activityDto.setMinScore(activity.getMinScore());
        activityDto.setDate(activity.getSchedule());

        return activityDto;
    }

    public static final Activity convertToActivityEntity(ActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setTitle(activityDto.getTitle());
        activity.setMaxScore(activityDto.getMaxScore());
        activity.setMinScore(activityDto.getMinScore());
        activity.setSchedule(activityDto.getDate());

        return activity;
    }
}

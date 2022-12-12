package com.yondu.university.project_rohan.controller;

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

        CourseClass courseClass = classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
        Activity quiz = convertToActivityEntity(quizDto);
        quiz.setCourseClass(courseClass);

        return new ActivityDto(this.activityService.saveNewQuiz(quiz));
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/exercises")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto saveNewExercise(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch,
            @RequestBody @Valid ActivityDto exerciseDto) {

        CourseClass courseClass = classService.findBySMEAndCourseAndBatch(currentUser, code, batch);
        Activity exercise = convertToActivityEntity(exerciseDto);
        exercise.setCourseClass(courseClass);

        return new ActivityDto(this.activityService.saveNewExercise(exercise));
    }

    @DeleteMapping(path = "quizzes/{id}/delete")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto deleteQuizById(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser, @PathVariable int id) {

        Activity quiz = this.activityService.deleteQuizByIdAndSMEEmail(id, currentUser);
        return new ActivityDto(quiz);
    }

    @DeleteMapping(path = "exercises/{id}/delete")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ActivityDto deleteExerciseById(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser, @PathVariable int id) {

        Activity exercise = this.activityService.deleteExerciseByIdAndSMEEmail(id, currentUser);
        return new ActivityDto(exercise);
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

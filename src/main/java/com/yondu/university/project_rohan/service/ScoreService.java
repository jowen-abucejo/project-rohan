package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.Score;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.repository.ScoreRepository;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ActivityService activityService;
    private final UserService userService;

    /**
     * @param scoreRepository
     * @param activityService
     * @param userService
     */
    public ScoreService(ScoreRepository scoreRepository, ActivityService activityService, UserService userService) {
        this.scoreRepository = scoreRepository;
        this.userService = userService;
        this.activityService = activityService;
    }

    public Score saveNewQuizScore(String smeEmail, int quizId, String studentEmail, Score score) {
        Optional<Activity> optionalActivity = this.activityService.findQuizByIdAndSMEEmail(quizId, smeEmail);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Quiz not found.");
        }

        Activity activity = optionalActivity.get();
        CourseClass courseClass = activity.getCourseClass();
        Course course = courseClass.getCourse();

        Optional<User> optionalStudent = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email doesn't match to any enrolled student.");
        }

        boolean quizIsGraded = this.scoreRepository.isExistsByActivityAndStudent(activity.getId(), studentEmail);
        if (quizIsGraded) {
            throw new ResourceNotFoundException("Not graded quiz with the given id not found.");
        }

        int adjustedScore = Math.max(score.getScore(), activity.getMinScore());
        adjustedScore = Math.min(adjustedScore, activity.getMaxScore());

        score.setScore(adjustedScore);
        score.setActivity(activity);
        score.setStudent(optionalStudent.get());

        return this.scoreRepository.save(score);
    }

    public Score saveNewExerciseScore(String smeEmail, int exerciseId, String studentEmail, Score score) {
        Optional<Activity> optionalActivity = this.activityService.findExerciseByIdAndSMEEmail(exerciseId, smeEmail);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Exercise not found.");
        }

        Activity activity = optionalActivity.get();
        CourseClass courseClass = activity.getCourseClass();
        Course course = courseClass.getCourse();

        Optional<User> optionalStudent = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email doesn't match to any enrolled student.");
        }

        boolean exerciseIsGraded = this.scoreRepository.isExistsByActivityAndStudent(activity.getId(), studentEmail);
        if (exerciseIsGraded) {
            throw new ResourceNotFoundException("Non-graded exercise with the given id not found.");
        }

        int adjustedScore = Math.max(score.getScore(), activity.getMinScore());
        adjustedScore = Math.min(adjustedScore, activity.getMaxScore());

        score.setScore(adjustedScore);
        score.setActivity(activity);
        score.setStudent(optionalStudent.get());

        return this.scoreRepository.save(score);
    }

    public Score saveNewProjectScore(String smeEmail, String courseCode, Integer batch, String studentEmail,
            Score score) {

        Optional<Activity> optionalActivity = this.activityService.findProjectByCourseClassAndSMEEmail(courseCode,
                batch, smeEmail);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Project not found.");
        }

        Activity project = optionalActivity.get();
        CourseClass courseClass = project.getCourseClass();
        Course course = courseClass.getCourse();

        Optional<User> optionalStudent = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Email doesn't match to any enrolled student.");
        }

        boolean projectIsGraded = this.scoreRepository.isExistsByActivityAndStudent(project.getId(), studentEmail);
        if (projectIsGraded) {
            throw new ResourceNotFoundException("Not graded class project not found.");
        }

        int adjustedScore = Math.max(score.getScore(), project.getMinScore());
        adjustedScore = Math.min(adjustedScore, project.getMaxScore());

        score.setScore(adjustedScore);
        score.setActivity(project);
        score.setStudent(optionalStudent.get());

        return this.scoreRepository.save(score);
    }
}

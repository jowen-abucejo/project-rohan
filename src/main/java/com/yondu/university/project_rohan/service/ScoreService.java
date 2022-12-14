package com.yondu.university.project_rohan.service;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.Score;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ParameterException;
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
        Activity quiz = this.activityService.findQuizByIdAndSMEEmail(quizId, smeEmail);
        CourseClass courseClass = quiz.getCourseClass();
        Course course = courseClass.getCourse();

        User student = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);

        boolean quizIsGraded = this.scoreRepository.isExistsByActivityAndStudent(quiz.getId(), studentEmail);
        if (quizIsGraded) {
            throw new ResourceNotFoundException("Not graded quiz with the given id not found.");
        }

        return this.saveScore(score, quiz, student);
    }

    public Score saveNewExerciseScore(String smeEmail, int exerciseId, String studentEmail, Score score) {
        Activity exercise = this.activityService.findExerciseByIdAndSMEEmail(exerciseId, smeEmail);
        CourseClass courseClass = exercise.getCourseClass();
        Course course = courseClass.getCourse();

        User student = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);

        boolean exerciseIsGraded = this.scoreRepository.isExistsByActivityAndStudent(exercise.getId(), studentEmail);
        if (exerciseIsGraded) {
            throw new ResourceNotFoundException("Not graded exercise with the given id not found.");
        }

        return this.saveScore(score, exercise, student);
    }

    public Score saveNewProjectScore(String smeEmail, String courseCode, Integer batch, String studentEmail,
            Score score) {

        Activity project = this.activityService.findProjectByCourseClassAndSMEEmail(courseCode,
                batch, smeEmail);

        CourseClass courseClass = project.getCourseClass();
        Course course = courseClass.getCourse();

        User student = this.userService.findStudentByCourseClassAndEmail(course.getCode(),
                courseClass.getBatchNumber(), studentEmail);

        boolean projectIsGraded = this.scoreRepository.isExistsByActivityAndStudent(project.getId(), studentEmail);
        if (projectIsGraded) {
            throw new ResourceNotFoundException("Not graded class project not found.");
        }

        return this.saveScore(score, project, student);

    }

    private boolean isScoreValid(Activity activity, Integer score) {
        return score >= activity.getMaxScore() && score <= activity.getMaxScore();
    }

    private Score saveScore(Score score, Activity activity, User student) {
        if (!this.isScoreValid(activity, score.getScore())) {
            throw new ParameterException(
                    String.format("Score can not be less than %d and greater than %d.", activity.getMinScore(),
                            activity.getMaxScore()));
        }

        score.setActivity(activity);
        score.setStudent(student);

        return this.scoreRepository.save(score);
    }
}

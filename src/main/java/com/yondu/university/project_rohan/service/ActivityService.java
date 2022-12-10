package com.yondu.university.project_rohan.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;
import com.yondu.university.project_rohan.repository.ActivityRepository;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final CourseClassService classService;

    private final String TYPE_QUIZ = "QUIZ";
    private final String TYPE_EXERCISE = "EXERCISE";
    private final String TYPE_PROJECT = "PROJECT";

    /**
     * @param activityRepository
     */
    public ActivityService(ActivityRepository activityRepository, CourseClassService classService) {
        this.activityRepository = activityRepository;
        this.classService = classService;
    }

    public Activity saveNewQuiz(Activity quiz) {
        quiz.setType(TYPE_QUIZ);
        return this.activityRepository.save(quiz);
    }

    public Activity saveNewExercise(Activity exercise) {
        exercise.setType(TYPE_EXERCISE);
        return this.activityRepository.save(exercise);
    }

    public Activity saveNewProject(Activity project) {
        project.setType(TYPE_PROJECT);
        return this.activityRepository.save(project);
    }

    public Optional<Activity> deleteQuiz(int quizId, int classId) {
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndClassIdAndType(quizId, classId,
                TYPE_QUIZ);
        return this.deleteActivity(optionalActivity);
    }

    public Optional<Activity> deleteExercise(int exerciseId, int classId) {
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndClassIdAndType(exerciseId, classId,
                TYPE_EXERCISE);
        return this.deleteActivity(optionalActivity);
    }

    public Optional<Activity> deleteQuizByIdAndSMEEmail(int quizId, String smeEmail) {
        Optional<Activity> optionalActivity = this.findQuizByIdAndSMEEmail(quizId, smeEmail);

        return this.deleteActivity(optionalActivity);
    }

    public Optional<Activity> deleteExerciseByIdAndSMEEmail(int exerciseId, String smeEmail) {
        Optional<Activity> optionalActivity = this.findExerciseByIdAndSMEEmail(exerciseId, smeEmail);

        return this.deleteActivity(optionalActivity);
    }

    public Optional<Activity> findQuizByIdAndSMEEmail(int quizId, String smeEmail) {
        return this.activityRepository.findByIdAndTypeAndSmeEmail(quizId, TYPE_QUIZ,
                smeEmail);
    }

    public Optional<Activity> findExerciseByIdAndSMEEmail(int exerciseId, String smeEmail) {
        return this.activityRepository.findByIdAndTypeAndSmeEmail(exerciseId, TYPE_EXERCISE,
                smeEmail);
    }

    public Optional<Activity> findProjectByCourseClassAndSMEEmail(String code, Integer batch, String smeEmail) {
        Optional<Activity> optionalActivity = this.activityRepository.findByClassAndTypeAndSmeEmail(TYPE_PROJECT, code,
                batch, smeEmail);
        if (optionalActivity.isPresent()) {
            return optionalActivity;
        } else {
            return Optional.of(this.createProject(smeEmail, code, batch));
        }

    }

    private Activity createProject(String smeEmail, String code, Integer batch) {
        Optional<CourseClass> optionalCourseClass = this.classService.findBySMEAndCourseAndBatch(smeEmail, code, batch);
        if (optionalCourseClass.isEmpty()) {
            throw new ResourceNotFoundException("Class with the given course and batch not found.");
        }

        Activity project = new Activity();
        project.setTitle(String.format("Project for %s batch %d", code, batch));
        project.setMaxScore(100);
        project.setMinScore(0);
        project.setSchedule(LocalDate.now());
        project.setCourseClass(optionalCourseClass.get());

        return this.saveNewProject(project);
    }

    private Optional<Activity> deleteActivity(Optional<Activity> optionalActivity) {
        if (optionalActivity.isPresent() && optionalActivity.get().getScores().isEmpty()) {
            this.activityRepository.delete(optionalActivity.get());
        }

        return optionalActivity;
    }
}

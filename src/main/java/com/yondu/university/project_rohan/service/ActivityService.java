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

    public Activity deleteQuizByIdAndSMEEmail(int quizId, String smeEmail) {
        return this.deleteActivity(this.findQuizByIdAndSMEEmail(quizId, smeEmail));
    }

    public Activity deleteExerciseByIdAndSMEEmail(int exerciseId, String smeEmail) {
        return this.deleteActivity(this.findExerciseByIdAndSMEEmail(exerciseId, smeEmail));
    }

    public Activity findQuizByIdAndSMEEmail(int quizId, String smeEmail) {
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndTypeAndSmeEmail(quizId, TYPE_QUIZ,
                smeEmail);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Quiz not found.");
        }
        return optionalActivity.get();
    }

    public Activity findExerciseByIdAndSMEEmail(int exerciseId, String smeEmail) {
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndTypeAndSmeEmail(exerciseId,
                TYPE_EXERCISE,
                smeEmail);
        if (optionalActivity.isEmpty()) {
            throw new ResourceNotFoundException("Exercise not found.");
        }
        return optionalActivity.get();
    }

    public Activity findProjectByCourseClassAndSMEEmail(String code, Integer batch, String smeEmail) {
        Optional<Activity> optionalActivity = this.activityRepository.findByClassAndTypeAndSmeEmail(TYPE_PROJECT, code,
                batch, smeEmail);
        if (optionalActivity.isEmpty()) {
            return this.createProject(smeEmail, code, batch);
        }

        return optionalActivity.get();
    }

    private Activity createProject(String smeEmail, String code, Integer batch) {
        CourseClass courseClass = this.classService.findBySMEAndCourseAndBatch(smeEmail, code, batch);

        Activity project = new Activity();
        project.setTitle(String.format("Project for %s batch %d", code, batch));
        project.setMaxScore(100);
        project.setMinScore(0);
        project.setSchedule(LocalDate.now());
        project.setCourseClass(courseClass);

        return this.saveNewProject(project);
    }

    private Activity deleteActivity(Activity activity) {
        if (activity.getScores().isEmpty()) {
            this.activityRepository.delete(activity);
        } else {
            throw new ResourceNotFoundException("Cannot be deleted");
        }

        return activity;
    }
}

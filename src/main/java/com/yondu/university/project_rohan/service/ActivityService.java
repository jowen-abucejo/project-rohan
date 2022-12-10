package com.yondu.university.project_rohan.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.repository.ActivityRepository;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    private final String TYPE_QUIZ = "QUIZ";
    private final String TYPE_EXERCISE = "EXERCISE";
    private final String TYPE_PROJECT = "PROJECT";

    /**
     * @param activityRepository
     */
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndTypeAndSmeEmail(quizId, TYPE_QUIZ,
                smeEmail);

        return this.deleteActivity(optionalActivity);
    }

    public Optional<Activity> deleteExerciseByIdAndSMEEmail(int exerciseId, String smeEmail) {
        Optional<Activity> optionalActivity = this.activityRepository.findByIdAndTypeAndSmeEmail(exerciseId,
                TYPE_EXERCISE, smeEmail);

        return this.deleteActivity(optionalActivity);
    }

    private Optional<Activity> deleteActivity(Optional<Activity> optionalActivity) {
        if (optionalActivity.isPresent() && optionalActivity.get().getScores().isEmpty()) {
            this.activityRepository.delete(optionalActivity.get());
        }

        return optionalActivity;
    }
}

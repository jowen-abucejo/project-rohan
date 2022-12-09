package com.yondu.university.project_rohan.service;

import org.springframework.stereotype.Service;

import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.repository.ActivityRepository;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    /**
     * @param activityRepository
     */
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity saveNewQuiz(Activity quiz) {
        return this.activityRepository.save(quiz);
    }
}

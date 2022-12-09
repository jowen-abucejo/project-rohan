package com.yondu.university.project_rohan.controller;

import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.dto.ActivityDto;
import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.service.ActivityService;
import com.yondu.university.project_rohan.service.CourseClassService;

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

    public static final ActivityDto convertToActivityDTO(Activity activity) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activity.getId() + "");
        activityDto.setCourseCode(activity.getCourseClass().getCourse().getCode());
        activityDto.setBatch(activity.getCourseClass().getBatchNumber());
        activityDto.setTitle(activity.getTitle());
        activityDto.setMaxScore(activity.getMaxScore());
        activityDto.setMinScore(activity.getMinScore());

        return activityDto;
    }

}

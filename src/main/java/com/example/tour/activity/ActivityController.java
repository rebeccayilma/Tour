package com.example.tour.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/activity")

public class ActivityController {
    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(path = "{activityId}")
    public Activity getActivity(@PathVariable("activityId") Long activityId) {
        return activityService.getActivity(activityId);
    }

    @PostMapping
    public void proposeNewActivity(@RequestBody Activity activity) {
        if (activity == null || activity.isActive()) return;
        activityService.addNewActivity(activity);
    }

}
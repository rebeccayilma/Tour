package com.example.tour.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void addNewActivity(Activity activity) {
        activityRepository.save(activity);
    }

    public Activity getActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new IllegalStateException("Activity with id " + activityId + " does not exist")
        );
        return activity;
    }

}

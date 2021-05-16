package com.example.tour.activity;

import com.example.tour.place.Place;
import com.example.tour.place.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final PlaceRepository placeRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, PlaceRepository placeRepository) {
        this.activityRepository = activityRepository;
        this.placeRepository = placeRepository;
    }

    public void addNewActivity(Activity activity) {
        Long placeId = activity.getPlace().getId();
        Place place = placeRepository.findById(placeId).orElseThrow(
                () -> new IllegalStateException("No place with id " + placeId)
        );
        activity.setPlace(place);
        activityRepository.save(activity);
    }

    public Activity getActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new IllegalStateException("Activity with id " + activityId + " does not exist")
        );
        return activity;
    }

    public List<Activity> getInactiveActivities() {
        return activityRepository.findAllByIsActive(false);
    }

    public List<Activity> getActiveActivities(Long placeId) {
        return activityRepository.findAllByIsActive(true)
                .stream()
                .filter(activity -> activity.getPlace().getId().equals(placeId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new IllegalStateException("Activity with id " + activityId + " does not exist")
        );
        activity.setActive(true);
    }

    @Transactional
    public void deactivateActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new IllegalStateException("Activity with id " + activityId + " does not exist")
        );

        activity.setActive(false);
    }
}

package com.TourSystem.toursystem.Activity;

import com.TourSystem.toursystem.models.Activity;
import com.TourSystem.toursystem.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ActivityService {


    @Autowired
    private ActivityRepository activityRepository;

    public void save(Activity activity) {

        activityRepository.save(activity);
    }

    public List<Activity> getAll() {
        Iterable<Activity> allActivities = activityRepository.findAll();

        return StreamSupport.stream(allActivities.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Activity> getAllByActive(Boolean active) {

        return activityRepository.findAllByActive(active);
    }


    public String getActivityInformation(String information) {

        return activityRepository.getByInformation(information);
    }

    public void deleteById(Long id) {

        activityRepository.deleteById(id);
    }




}

package com.TourSystem.toursystem.Activity;

import com.TourSystem.toursystem.models.Activity;
import com.TourSystem.toursystem.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    @GetMapping(value = "/activities")
    public List<Activity> getAllActivities() {

        return activityService.getAll();
    }

    @GetMapping(value = "/activities/active/{active}")
    public List<Activity> getAllActivities(@PathVariable Boolean active) {
        return activityService.getAllByActive(active);
    }

    @RequestMapping("/activities/{activityId}/information")
    public String getDescription(@PathVariable String information){

        return activityService.getActivityInformation(information);
    }


    @PostMapping(value = "/activities")
    public void saveActivity(@RequestBody Activity activity) {
        activityService.save(activity);
    }

    @DeleteMapping(value = "/activities/id/{id}")
    public void deleteActivityById(@PathVariable Long id) {
        activityService.deleteById(id);
    }
}

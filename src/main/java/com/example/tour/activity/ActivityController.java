package com.example.tour.activity;

import com.example.tour.TransformerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activity")
public class ActivityController {
    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(path = "{activityId}")
    public ActivityDTO getActivity(@PathVariable("activityId") Long activityId) {
        return TransformerUtils.createActivityDTO(activityService.getActivity(activityId));
    }

    @PostMapping
    public void proposeNewActivity(@RequestBody Activity activity) {
        if (activity == null || activity.isActive()) return;
        System.out.println(activity);
        activityService.addNewActivity(activity);
    }

    @PutMapping(path = "approve/{activityId}")
    public void approveActivity(@PathVariable("activityId") Long activityId) {
        activityService.approveActivity(activityId);
    }

    @GetMapping(path = "inactive")
    public List<ActivityDTO> listInActiveActivities() {
        return TransformerUtils.createListActivityDTO(activityService.getInactiveActivities());
    }

    @PutMapping(path = "deactivate/{activityId}")
    public void deactivateActivity(@PathVariable("activityId") Long activityId) {
        activityService.deactivateActivity(activityId);
    }

    @GetMapping(path = "active")
    public List<ActivityDTO> listActiveActivities() {
        return TransformerUtils.createListActivityDTO(activityService.getActiveActivities());
    }

}
package com.example.tour;

import com.example.tour.activity.Activity;
import com.example.tour.activity.ActivityDTO;
import com.example.tour.place.Place;
import com.example.tour.place.PlaceDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class TransformerUtils {
    public static PlaceDTO createPlaceDTO(Place place) {
        PlaceDTO placeDTO = new PlaceDTO();
        BeanUtils.copyProperties(place, placeDTO);
        return placeDTO;
    }

    public static ActivityDTO createActivityDTO(Activity activity) {
        ActivityDTO activityDTO = new ActivityDTO();
        BeanUtils.copyProperties(activity, activityDTO, "place");
        activityDTO.setPlace(createPlaceDTO(activity.getPlace()));

        return activityDTO;
    }

    public static List<ActivityDTO> createListActivityDTO(List<Activity> inActiveActivities) {
        List<ActivityDTO> activityList = new ArrayList<>();
        inActiveActivities.forEach(activity -> {
            activityList.add(createActivityDTO(activity));
        });
        return activityList;
    }

    public static List<PlaceDTO> createListPlaceDTO(List<Place> places) {
        List<PlaceDTO> placeList = new ArrayList<>();
        places.forEach(place -> {
            placeList.add(createPlaceDTO(place));
        });
        return placeList;
    }
}

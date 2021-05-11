package com.example.tour;

import com.example.tour.activity.Activity;
import com.example.tour.activity.ActivityDTO;
import com.example.tour.place.Place;
import com.example.tour.place.PlaceDTO;

public class TransformerUtils {
    public static PlaceDTO createPlaceDTO(Place place) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setId(place.getId());
        placeDTO.setName(place.getName());
        placeDTO.setLatitude(place.getLatitude());
        placeDTO.setLongitude(place.getLongitude());
        placeDTO.setDescription(place.getDescription());

        return placeDTO;
    }

    public static ActivityDTO createActivityDTO(Activity activity) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(activity.getId());
        activityDTO.setInfo(activity.getInfo());
        activityDTO.setActive(activity.isActive());
        activityDTO.setPlace(createPlaceDTO(activity.getPlace()));

        return activityDTO;
    }
}

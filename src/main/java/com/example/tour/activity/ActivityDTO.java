package com.example.tour.activity;

import com.example.tour.place.PlaceDTO;
import com.example.tour.rating.RatingDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ActivityDTO {
    @JsonProperty(value= "activity_id")
    private Long id;
    @JsonProperty(value= "info")
    private String info;
    @JsonProperty(value= "is_active")
    private boolean isActive;
    @JsonProperty(value= "place")
    private PlaceDTO place;
    @JsonProperty(value= "image_path")
    private String imagePath;
    @JsonProperty(value= "ratings")
    private List<RatingDTO> ratings;

}

package com.example.tour.activity;

import com.example.tour.place.PlaceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

    public String toString() {
        return
            "[Id: " + id + ", info: " + info + ", active: " + isActive + ", place: " + place.getName() + "]";
    }
}

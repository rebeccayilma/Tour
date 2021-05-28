package com.example.tour.place;

import com.example.tour.activity.ActivityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlaceDTO {
    @JsonProperty(value= "place_id")
    private Long id;
    @JsonProperty(value= "name")
    private String name;
    @JsonProperty(value= "latitude")
    private double latitude;
    @JsonProperty(value= "longitude")
    private double longitude;
    @JsonProperty(value= "description")
    private String description;
    @JsonProperty(value= "images")
    private List<ImageDTO> images;

    public String toString () {
        return
            "[Id: " + id + ", name: " + name +
            ", latitude: " + latitude + ", longitude: " + longitude +
            ", description: " + description + "]";
    }
}

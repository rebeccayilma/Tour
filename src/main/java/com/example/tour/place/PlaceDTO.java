package com.example.tour.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
}

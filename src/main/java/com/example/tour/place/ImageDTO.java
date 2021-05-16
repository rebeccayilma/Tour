package com.example.tour.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageDTO {
    @JsonProperty(value= "id")
    private Long id;
    @JsonProperty(value= "path")
    private String path;
    @JsonProperty(value= "place")
    private PlaceDTO place;
}

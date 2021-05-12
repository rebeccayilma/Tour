package com.example.tour.rating;

import com.example.tour.activity.ActivityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
@Data
public class RatingDTO {
    @JsonProperty(value= "rating_id")
    private Long id;
    @JsonProperty(value= "date")
    private LocalDate date;
    @JsonProperty(value= "score")
    private int score;
    @JsonProperty(value= "activity")
    private ActivityDTO activity;
}



package com.example.tour.rating;

import com.example.tour.TransformerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("api/rating")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("{activity_id}")
    public List<RatingDTO> getAllRating(@PathVariable(value = "activity_id") Long activityId){
        return TransformerUtils.createListRatingDTO(ratingService.getRatings(activityId));
    }

    @PostMapping
    public void addNewRating(@RequestBody Rating rating) {
        ratingService.addNewRating(rating);
    }

}

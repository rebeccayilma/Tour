package com.example.tour.rating;

import com.example.tour.TransformerUtils;
import com.example.tour.activity.Activity;
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
    @GetMapping
    public List<RatingDTO> getAllRating(){
        return TransformerUtils.createListRatingDTO(ratingService.getRatings());
    }

    @PostMapping
    public void addNewRating(@RequestBody Activity activity, Rating rating) {
        if (activity == null || activity.isActive()) return;
        ratingService.addNewRating(rating);
    }

}

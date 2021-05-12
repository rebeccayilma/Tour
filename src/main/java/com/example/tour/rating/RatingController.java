package com.example.tour.rating;

import com.example.tour.TransformerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rating/")

public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @GetMapping({"activity_id"})
    public List<RatingDTO> getAllRating(@PathVariable("activity_id") Long activity_id){
        return TransformerUtils.createListRatingDTO(ratingService.getRatings(activity_id));
    }

    @PostMapping
    public void addNewRating(@RequestBody Rating rating) {
         ratingService.addNewRating(rating);
    }

}

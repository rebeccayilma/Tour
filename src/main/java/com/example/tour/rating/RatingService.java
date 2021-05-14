package com.example.tour.rating;

import com.example.tour.activity.Activity;
import com.example.tour.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private RatingRepository ratingRepository;
    private ActivityRepository activityRepository;


    @Autowired
    public RatingService(RatingRepository ratingRepository, ActivityRepository activityRepository) {
        this.ratingRepository = ratingRepository;
        this.activityRepository = activityRepository;


    }

    public List<Rating> getRatings(Long activityId) {
        return ratingRepository.findByActivityId(activityId);
    }

    public void addNewRating(Rating rating) {
        Long activityId = rating.getActivity().getId();
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new IllegalStateException("No rating exist for " + activityId + " activity"));
        rating.setActivity(activity);
        ratingRepository.save(rating);
    }
}

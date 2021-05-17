package com.example.tour.rating;

import com.example.tour.activity.Activity;
import com.example.tour.activity.ActivityRepository;
import com.example.tour.activity.ActivityService;
import com.example.tour.place.Place;
import com.example.tour.place.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class RatingServiceTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private ActivityRepository activityRepository;
    private Activity activity;
    private Place place;
    private Rating rating;
    private ActivityService activityService;
    @Mock
    private PlaceRepository placeRepository;
    private RatingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RatingService(ratingRepository, activityRepository);
        activityService = new ActivityService(activityRepository,placeRepository);
    }
    @Test
    void canGetPlaces() {
        Activity activity = new Activity();
        Rating rating = new Rating(LocalDate.of(2021,03,25),4, activity);
        when(ratingRepository.findByActivityId(anyLong()))
                .thenReturn(java.util.List.of());
        underTest.getRatings(3L);
        //assertion
        verify(ratingRepository).findByActivityId(3L);
    }
    @Test
    void addNewRating() {
        place = new Place(5L,
                "place",
                10.0,
                20.0,
                "description",
                new ArrayList<>(),
                new ArrayList<>());
        activity = new Activity("info", place);
        activity.setId(1L);
        rating = new Rating(LocalDate.now(),4, activity);
        rating.setActivity(activity);
        when(activityRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.ofNullable(activity));
        underTest.addNewRating(rating);

            assertEquals(activity, rating.getActivity());
            verify(ratingRepository).save(rating);
    }
}
package com.example.tour.activity;

import com.example.tour.place.Place;
import com.example.tour.rating.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity activity;
    private Place place;
    private Rating rating;

    @BeforeEach
    void setUp() {
        place = Mockito.mock(Place.class);
    }

    @Test
    void IsInActiveWhenCreated() {
        activity = new Activity("info", place);
        assertFalse(activity.isActive());
    }

    @Test
    void placeCannotBeNull() {
        activity = new Activity("info", place);
        assertNotNull(activity.getPlace());
    }

    @Test
    void canAddRating() {
        rating = Mockito.mock(Rating.class);
        activity = new Activity("info", place);
        activity.addRating(rating);
        assertEquals(1, activity.getRatings().size());
    }
}
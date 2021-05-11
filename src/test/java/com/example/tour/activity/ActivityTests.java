package com.example.tour.activity;

import com.example.tour.place.Place;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ActivityTests {

    // TODO: Add tests regarding endpoints themselves

    @Test
    void proposeActivitySuccess() {
        Place place = new Place("thisPlace", 12.32, 13.45, "Welcome to Nigeria");
        String activityInfo = "Great activity";
        Activity activity = new Activity(activityInfo, place);
        place.setActivities(List.of(activity));

        assertNotNull(place);
        assertNotNull(place.getActivities());
        assertEquals(place.getActivities().size(), 1);
        assertEquals(place.getActivities().get(0).getInfo(), activityInfo);
    }
}

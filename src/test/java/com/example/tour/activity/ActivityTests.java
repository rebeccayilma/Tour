package com.example.tour.activity;

import com.example.tour.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ActivityTests {
    // TODO: Add tests regarding endpoints themselves

    private Place emptyPlaceOk;
    private Place placeOk;
    private Activity newActivityOk;
    private Activity approvedActivityOk;
    private final String activityInfo = "Great activity";

    @BeforeEach
    void setUp() {
        emptyPlaceOk = new Place("Hello Place", 78, 87, "Dunnow where");
        placeOk = new Place("commonPlace", 12.32, 13.45, "Welcome to Nigeria");
        newActivityOk = new Activity("commonActivity", placeOk);

        approvedActivityOk = new Activity("approvedActivity", placeOk);
        approvedActivityOk.setActive(true);
    }

    // -------------------------
    // New Activity tests
    // -------------------------

    @Test
    void newActivitySuccess() {
        String activityInfo = "Great activity";
        Activity activity = new Activity(activityInfo, emptyPlaceOk);

        assertNotNull(activity);

        assertNotNull(activity.getInfo());
        assertEquals(activity.getInfo(), activityInfo);

        assertNotNull(activity.getPlace());
        assertEquals(activity.getPlace(), emptyPlaceOk);

        assertFalse(activity.isActive());

        assertNotNull(emptyPlaceOk.getActivities());
        assertEquals(emptyPlaceOk.getActivities().size(), 1);
        assertEquals(emptyPlaceOk.getActivities().get(0).getInfo(), activityInfo);
    }

    @Test
    void newActivityFailureNullArgs() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Activity(null, null)
        );
    }

    @Test
    void newActivityFailureNullInfo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Activity(null, placeOk),
                "Place where activity is located must not be null"
        );
    }

    @Test
    void newActivityFailureNullPlace() {
        String activityInfo = "Great activity";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Activity(activityInfo, null),
                "Place where activity is located must not be null"
        );
    }

    // -------------------------
    // Approve Activity tests
    // -------------------------
    @Test
    void approveActivitySuccess() {
        newActivityOk.setActive(true);
        assertTrue(newActivityOk.isActive());
    }

}

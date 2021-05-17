package com.example.tour.activity;

import com.example.tour.place.Place;
import com.example.tour.place.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private PlaceRepository placeRepository;

    private Activity activity;
    private Place place;

    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        activityService = new ActivityService(activityRepository, placeRepository);
    }

    @Test
    void canAddNewActivity() {
        // setup
        place = new Place(1L,
                "place",
                10.0,
                20.0,
                "description",
                new ArrayList<>(),
                new ArrayList<>());
        activity = new Activity("info", place);
        when(placeRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.ofNullable(place));
        activityService.addNewActivity(activity);

        // assertion
        assertEquals(place, activity.getPlace());
        verify(activityRepository).save(activity);
    }

    @Test
    void canNotAddNewActivityWithOutValidPlaceId() {
        // setup
        long wrongPlaceId = 10L;
        String exceptionMessage = "Activity with id " + wrongPlaceId + " does not exist";
        place = new Place(wrongPlaceId,
                "place",
                10.0,
                20.0,
                "description",
                new ArrayList<>(),
                new ArrayList<>());
        activity = new Activity("info", place);
        when(placeRepository.findById(anyLong()))
                .thenThrow(new IllegalStateException(exceptionMessage));

        // assertion
        assertThrows(
            IllegalStateException.class,
            () -> activityService.addNewActivity(activity),
            exceptionMessage
        );
        verify(activityRepository, never()).save(activity);
    }
    @Test
    void canGetActivity() {
        // setup
        place = new Place();
        activity = new Activity("info", place);
        when(activityRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.ofNullable(activity));
        activityService.getActivity(1L);

        // assertion
        verify(activityRepository).findById(1L);
    }
    @Test
    void throwsWhenActivityNotFound() {
        // setup
        long wrongActivityId = 10L;
        String exceptionMessage = "Activity with id " + wrongActivityId + " does not exist";
        place = new Place();
        activity = new Activity("info", place);
        when(activityRepository.findById(anyLong()))
                .thenThrow(new IllegalStateException(exceptionMessage));

        // assertion
        assertThrows(
            IllegalStateException.class,
            () -> activityService.getActivity(wrongActivityId),
            exceptionMessage
        );
    }
    @Test
    void canGetInActiveActivities() {
        // setup
        activityService.getInactiveActivities();

        //assertion
        verify(activityRepository).findAllByIsActive(false);
    }

    @Test
    void approveActivity() {
        // setup
        place = new Place();
        activity = new Activity("info", place);
        activity.setActive(false);
        when(activityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(activity));
        activityService.approveActivity(1L);

        // assert
        assertTrue(activity.isActive());

    }

    @Test
    void deactivateActivity() {
        // setup
        place = new Place();
        activity = new Activity("info", place);
        activity.setActive(true);
        when(activityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(activity));
        activityService.deactivateActivity(1L);

        // assert
        assertFalse(activity.isActive());
    }
}
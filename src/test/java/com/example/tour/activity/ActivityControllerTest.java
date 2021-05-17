package com.example.tour.activity;


import com.example.tour.place.Place;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {


    @InjectMocks
    ActivityController activityController;

    @Mock
    private ActivityService activityService;


    private Place stubPlace(String name, double latitude, double longitude, String desc) {
        return new Place(name, latitude, longitude, desc);
    }

    private Activity stubActivity() {
        Place stubPlace = stubPlace("NewPlace", 23.36, 52.36, "Welcome to Addis Ababa");
        return new Activity("approvedActivity", stubPlace);
    }

    @Test
    public void testGetActivity() {
        Activity stubActivity = stubActivity();
        when(activityService.getActivity(1L)).thenReturn(stubActivity);
        ActivityDTO activityDTO = activityController.getActivity(1L);
        assertThat(activityDTO.getInfo()).isEqualTo(stubActivity.getInfo());
        verify(activityService, times(1)).getActivity(1L);
    }

    @Test
    public void testListOfInactiveActivities() {
        List<Activity> allActivities = Arrays.asList(stubActivity(), stubActivity());
        when(activityService.getInactiveActivities()).thenReturn(allActivities);
        List<ActivityDTO> activityDTOList = activityController.listInactiveActivities();
        assertThat(activityDTOList.size()).isEqualTo(2);
        assertThat((activityDTOList.get(0).getInfo())).isEqualTo(allActivities.get(0).getInfo());
        assertFalse((allActivities.get(0).isActive()));
        verify(activityService, times(1)).getInactiveActivities();
    }

    @Test
    public void testApproveActivity() {
        ActivityController activityController1 = mock(ActivityController.class);
        doNothing().when(activityController1).approveActivity(1L);
        activityController1.approveActivity(1L);
        verify(activityController1, times(1)).approveActivity(1L);
    }

    @Test
    void testListActiveActivities(){

        Activity activity1 = stubActivity();
        activity1.setActive(true);
        Activity activity2 = stubActivity();
        activity2.setActive(true);
        List<Activity> allActivities = Arrays.asList(activity2,activity1);
        when(activityService.getActiveActivities(activity2.getPlace().getId())).thenReturn(allActivities);
        List<ActivityDTO> activityDTOList = activityController.listActiveActivities(activity2.getPlace().getId());
        assertThat(activityDTOList.size()).isEqualTo(2);
        assertThat((activityDTOList.get(0).getInfo())).isEqualTo(allActivities.get(0).getInfo());
        assertTrue((allActivities.get(0).isActive()));

        verify(activityService, times(1)).getActiveActivities(activity1.getPlace().getId());
    }

    @Test
   public void testDeactivateActivity(){
    ActivityController activityController1 = mock(ActivityController.class);
    doNothing().when(activityController1).deactivateActivity(1L);
    activityController1.deactivateActivity(1L);
    verify(activityController1, times(1)).deactivateActivity(1L);

    }

}
package com.example.tour.place;

import static org.junit.jupiter.api.Assertions.*;

import com.example.tour.activity.ActivityController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceControllerTest {

    @InjectMocks
    PlaceController placeController;

    @Mock
    private PlaceService placeService;

    @Test
    public void testGetPlace() {
        Place stubPlace = stubPlace("NewPlace", 23.36, 52.36, "Welcome to Addis Ababa");
        when(placeService.getPlace(1L)).thenReturn(stubPlace);
        PlaceDTO placeDTO = placeController.getPlace(1L);
        assertThat(placeDTO.getName()).isEqualTo(stubPlace.getName());
        verify(placeService, times(1)).getPlace(anyLong());
    }

    @Test
    public void testGetPlaces() {
        List<Place> allPlaces = Arrays.asList(stubPlace("NewPlace", 23.36, 52.36, "Welcome to Addis Ababa"),
                stubPlace("NewPlace 2", 21.36, 50.36, "Welcome to Nairobi"));

        when(placeService.getPlaces()).thenReturn(allPlaces);

        List<PlaceDTO> placeDTOList = placeController.getPlaces();

        assertThat(placeDTOList.size()).isEqualTo(2);

        assertThat((placeDTOList.get(0).getName())).isEqualTo(allPlaces.get(0).getName());
        verify(placeService, times(1)).getPlaces();
    }

    @Test
    public void testAddNewPlace(){
        Place place = stubPlace("NewPlace 2", 21.36, 50.36, "Welcome to Nairobi");
        placeController.addNewPlace(place);
        verify(placeService, times(1)).addNewPlace(place);

    }

    private Place stubPlace(String name, double latitude, double longitude, String desc) {
        return new Place(name, latitude, longitude, desc);
    }

}
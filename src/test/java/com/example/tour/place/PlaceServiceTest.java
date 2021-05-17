package com.example.tour.place;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock
    private PlaceRepository placeRepository;
    private PlaceService placeService;

    @BeforeEach
    void setUp() {

        placeService = new PlaceService(placeRepository);
    }
    @Test
    void canGetPlaces() {
        placeService.getPlaces();
        verify(placeRepository).findAll();
    }
    @Test
    void canAddNewPlace() {
        Place place = new Place("NewPlace", 23.36, 52.36, "Welcome to Addis Ababa");
        placeService.addNewPlace(place);
        ArgumentCaptor<Place> placeArgumentCaptor = ArgumentCaptor.forClass(Place.class);
        verify(placeRepository).save(placeArgumentCaptor.capture());
        Place capturedPlace = placeArgumentCaptor.getValue();
        assertThat(capturedPlace).isEqualTo(place);
    }
    @Test
    void canGetPlace() {
        Place place = new Place();
        when(placeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(place));
        placeService.getPlace(1L);
        verify(placeRepository).findById(1L);
    }
}

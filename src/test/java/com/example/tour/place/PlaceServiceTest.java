package com.example.tour.place;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock
    private PlaceRepository placeRepository;
    private PlaceService underTest;

    @BeforeEach
    void setUp() {
      underTest = new PlaceService(placeRepository);
    }
    @Test
    void canGetPlaces() {
        underTest.getPlaces();
        verify(placeRepository).findAll();
    }
    @Test
    void canAddNewPlace() {
        //given
        Place place = new Place("NewPlace", 23.36, 52.36, "Welcome to Addis Ababa");
        //when
        underTest.addNewPlace(place);
        //then
        ArgumentCaptor<Place> placeArgumentCaptor = ArgumentCaptor.forClass(Place.class);
        verify(placeRepository).save(placeArgumentCaptor.capture());

        Place capturedPlace = placeArgumentCaptor.getValue();
        assertThat(capturedPlace).isEqualTo(place);
    }
    @Test
    void canGetPlace() {
        // setup
        Place place = new Place();

        when(placeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(place));
        underTest.getPlace(1L);

        // assertion
        verify(placeRepository).findById(1L);
    }
}

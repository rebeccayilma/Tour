package com.example.tour.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    private Place place = new Place();
    @Test
    void getPlaces() {
        List<Place> places = placeService.getPlaces();
        assertNotNull(places);
        assertTrue(places.size() > 0);
    }

    @Test
    void addNewPlace() {
        place.setName("uganda");
        place.setDescription("the place is cool");
        place.setLatitude(23.78);
        place.setLongitude(56.75);
        placeService.addNewPlace(place);
        assertNotNull(place);
        assertTrue(place.getName().equals("uganda"));
        place = null;
        assertNull(place);
    }

    //get place by id.
    @Test
    void getPlace() {
        Place place = placeService.getPlace(Long.valueOf(1));
        assertNotNull(place);
        assertEquals("nigeria", place.getName());
    }
}
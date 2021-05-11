package com.example.tour.place;

import com.example.tour.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/place")

public class PlaceController {
    private final PlaceService placeService;
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> getPlaces(){
        return placeService.getPlaces();
    }

    @GetMapping(path = "{placeId}")
    public Place getPlace(@PathVariable("placeId") Long placeId){
        return placeService.getPlace(placeId);
    }

    @PostMapping
    public void addNewPlace(@RequestBody Place place) {
        if (place == null) return;
        placeService.addNewPlace(place);
    }
}

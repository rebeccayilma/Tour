package com.example.tour.place;

import com.example.tour.TransformerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;


    @PreAuthorize("hasAnyRole()")
    @GetMapping
    public List<PlaceDTO> getPlaces(){
        return TransformerUtils.createListPlaceDTO(placeService.getPlaces());
    }

    @GetMapping(path = "{placeId}")
    public PlaceDTO getPlace(@PathVariable("placeId") Long placeId){
        return TransformerUtils.createPlaceDTO(placeService.getPlace(placeId));
    }

    @PostMapping
    public void addNewPlace(@RequestBody Place place) {
        if (place == null) return;
        placeService.addNewPlace(place);
    }
}

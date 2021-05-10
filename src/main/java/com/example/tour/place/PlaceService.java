package com.example.tour.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;

    }
    public List<Place> getPlaces(){
        return placeRepository.findAll();
    }

}

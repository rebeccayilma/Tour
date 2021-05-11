package com.example.tour.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public void addNewPlace(Place place) {
        placeRepository.save(place);
    }

    public Place getPlace(Long placeId) {
        Optional<Place> place = placeRepository.findById(placeId);
        if (place.isPresent()) {
            return place.get();
        } else {
            throw new IllegalStateException("Place with id " + placeId + " does not exist");
        }
    }
}

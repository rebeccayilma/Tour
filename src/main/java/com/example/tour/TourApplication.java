package com.example.tour;

import com.example.tour.activity.Activity;
import com.example.tour.activity.ActivityRepository;
import com.example.tour.place.Place;
import com.example.tour.place.PlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TourApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourApplication.class, args);
    }

    @Bean
CommandLineRunner commandLineRunner(PlaceRepository placeRepository, ActivityRepository activityRepository){
        return args -> {
            Place us = new Place("USAYUAWSWE", 62.356, 56.358, "Fairfield and Iowa" );
            //placeRepository.save(us);

            Activity act = new Activity("New activity",us);
            activityRepository.save(act);
//            Place su = new Place("Canada", 58.356, 85.358, "New Place to b visited" );

            //placeRepository.saveAll(List.of(us,su));
            //placeRepository.findPlaceByName("USAYU")
            //.ifPresentOrElse(System.out::println, ()->System.out.println("Place is Not found"));

};
}
}

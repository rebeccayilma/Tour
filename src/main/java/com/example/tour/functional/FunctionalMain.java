package com.example.tour.functional;

import com.example.tour.authentication.model.User;
import com.example.tour.place.Place;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.example.tour.functional.TourUtilFunctions.*;

public class FunctionalMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        char option = 'z';
        Scanner keyboard = new Scanner(System.in);
        MemoryBank.setUp();

        while (true) {
            // ------MENU-------
            System.out.println("Please, choose one of the following functions to execute: ");
            System.out.println(
                    "\ta) Given a list of places, get the Contributor with more proposed activities");
            System.out.println(
                    "\tb) Given a list of places and a date, get all activities that have been rated before that date");
            System.out.println(
                    "\tc) Given a User which is a Contributor and a base score, get the list of places' names in the South Hemisphere in which they have rated an activity greater than said score");

            System.out.println(
                    "\tx) Given a User which is a Contributor and a base score, get the list of places' names in the South Hemisphere in which they have rated an activity greater than said score");

            // ---WAIT-INPUT---
            System.out.println("Your option:");
            option = keyboard.next().charAt(0);

            // ---PROCESSING---
            List<Place> places;
            User contributor;
            switch (option) {
                case 'a':
                    System.out.println("Looking for Contributor with more proposed activities in places from Memory Bank:");
                    places = MemoryBank.getPlaces();

                    // Expected output: contrib0
                    System.out.println(contributorWithMoreProposedActivities.apply(places).get());
                    break;
                case 'b':
                    System.out.println("Looking for all activities rated before 01-01-2021:");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Id: null, info: Great Activity 0, active: true, place: Fairfield]
                    System.out.println(
                        activitiesRatedBefore.apply(places, LocalDate.of(2021, 1, 1))
                    );
                    break;
                case 'c':
                    System.out.println("Looking for places in the South Hemisphere with activities rated by contrib3 with 3 or more:");
                    contributor = MemoryBank.getContributors().get(3);

                    // Expected output: [Buenos Aires]
                    System.out.println(placeNamesWithContribHighRatingsInSouthHemisphere.apply(contributor, 3));
                    break;
                // ADD MORE FUNCTIONS HERE
                case 'x':
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            System.out.println("Enter a key to continue...");
            keyboard.next();

            // --CLEAR-SCREEN--
            // TODO
            //  System.out.print("\033[H\033[2J");
            //  System.out.flush();
        }
    }
}

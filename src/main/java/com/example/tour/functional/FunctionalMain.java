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
                    "\td) Given a User which is an Admin and a year, get the most active activity in said year");
            System.out.println(
                    "\te) Given a list of places and a year, get the most active place in said year");
            System.out.println(
                    "\tf) Given a list of places and a year, get the Contributor who proposed the top rated activity");

            System.out.println(
                    "\tg) Given a place and a base rating, get all activities with average rating greater than the base");
            System.out.println(
                    "\th) Given a place, get the average rating of all its active activities");
            System.out.println(
                    "\ti) Given a list of places and a base number of acceptance, get those places that have at least that base number of active activities");

            System.out.println(
                    "\tj) Given places, radius and location(lat, long), get k highly-rated activities found in the area covered by the given radius from the given location");
            System.out.println(
                    "\tk) Given places, radius and location(lat, long), get top 5 activities found in the area covered by the given radius from the given location");
            System.out.println(
                    "\tl) Given places, find the admin who approved the lowest-rated activities");
            System.out.println(
                    "\tm) find activities that are the most recent and are highly rated");
            System.out.println(
                    "\tn) Given places, find a user who got the most ratings");
            System.out.println(
                    "\to) Given a place, find a percentage of unapproved activities");
            System.out.println(
                    "\tp) Given places, find the most active year");
            System.out.println(
                    "\tq) Given places and a User, find the most active year for that user");

            System.out.println(
                    "\tx) Exit");

            // ---WAIT-INPUT---
            System.out.println("Your option:");
            option = keyboard.next().charAt(0);

            // ---PROCESSING---
            List<Place> places;
            Place place;
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
                case 'd':
                    System.out.println("Looking for the most active activity for admin0:");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Id: null, info: Great Activity 0, active: true, place: Fairfield], [Id: null, info: Activity, active: true, place: Fairfield]
                    System.out.println(topRatedActivityByAdmin.apply(places, "admin0", 2));
                    break;
                case 'e':
                    System.out.println("Looking for most active place in 2021:");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Id: null, name: Fairfield, latitude: 41.0, longitude: 92.0, description: Home of MIU]
                    System.out.println(mostActivePlaceInAYear.apply(places, 2021, 3).get());
                    break;
                case 'f':
                    System.out.println("Looking for Contributor who proposed top activities in 2021:");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Id: null, username: contrib3, role: CONTRIBUTOR]
                    System.out.println(contributorWhoProposedTopActivities.apply(places, 2021).orElse(null));
                    break;
                case 'g':
                    System.out.println("Looking for activities with average rating greater than 3");
                    place = MemoryBank.getPlaces().get(0);

                    // Expected output: [Id: null, info: Great Activity 0, active: true, place: Fairfield]
                    System.out.println(activitiesWithAverageRatingGreaterThanK.apply(place, 3.0));
                    break;
                case 'h':
                    System.out.println("Looking for average rating of active activities");
                    place = MemoryBank.getPlaces().get(0);

                    // Expected output: [1.0533333333333332]
                    System.out.println(averageRatingActiveActivitiesInPlace.apply(place).getAsDouble());
                    break;
                case 'i':
                    System.out.println("Looking for places with more than 2 active activities");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Fairfield, Kigali]
                    System.out.println(placesWithMoreThanKActivities.apply(places, 2));
                    break;
                case 'j':
                    System.out.println("Looking for highly rated activities in the area");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Great Activity 0, Activity]
                    System.out.println(topKActivitiesNearMe.apply(places, 10.0, 41.01, 92.01, 2));
                    break;
                case 'k':
                    System.out.println("Looking for top 5 rated activities in the area");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Great Activity 0, Activity, Great Activity 2, Great Activity with really really really long description, Great Activity with not so long description]
                    System.out.println(top5ActivitiesInside10KmRadius.apply(places, 41.01, 92.01));
                    break;
                case 'l':
                    System.out.println("Looking for worst Admin (admin who has the list rated activities approved by him)");
                    places = MemoryBank.getPlaces();

                    // Expected output: admin0
                    System.out.println(worstAdmin.apply(places));
                    break;
                case 'm':
                    System.out.println("Looking for most recent and highly rated activities");
                    places = MemoryBank.getPlaces();

                    // Expected output: [Great Activity 2, Great Activity with really really really long description, Great Activity with not so long description, Kayak, Football]
                    System.out.println(mostRecentAndHighlyRatedKActivities.apply(places, 5));
                    break;
                case 'n':
                    System.out.println("Looking for star user");
                    places = MemoryBank.getPlaces();
                    // Expected output: [contrib0]
                    System.out.println(starUsers.apply(places, LocalDate.of(2021, 5, 13)));
                    break;
                case 'o':
                    System.out.println("Looking for percentage of unapproved activities in a place");
                    place = MemoryBank.getPlaces().get(0);
                    // Expected output: 16.667
                    System.out.println(percentageOfNotApprovedActivities.apply(place));
                    break;
                case 'p':
                    System.out.println("Looking for the most active year");
                    places = MemoryBank.getPlaces();
                    // Expected output: 2021
                    System.out.println(mostActiveYear.apply(places));
                    break;
                case 'q':
                    System.out.println("Looking for the most active year for user contrib0");
                    places = MemoryBank.getPlaces();
                    // Expected output: 2021
                    System.out.println(mostActiveYearByActivitiesUserSpecific.apply(places, "contrib0"));
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

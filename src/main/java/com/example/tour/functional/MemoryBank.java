package com.example.tour.functional;

import com.example.tour.activity.Activity;
import com.example.tour.authentication.model.User;
import com.example.tour.place.Place;
import com.example.tour.rating.Rating;

import java.time.LocalDate;
import java.util.List;

public class MemoryBank {
    private static List<Place> places;
    private static List<User> admins;
    private static List<User> contribs;
    private static List<Activity> activities;
    private static List<Rating> ratings;

    public static void setUp () {
        places = List.of(
                new Place("Fairfield", 41, 92, "Home of MIU"),
                new Place("Buenos Aires", -34.603722, -58.381592, "Capital of Argentina"),
                new Place("Addis Ababa", 9.145, 38.763611, "Capital of Ethiopia"),
                new Place("Kigali", -1.9397, 30.0557, "Capital of Rwanda"),
                new Place("New Delhi", 28.644800, 77.216721, "Capital of India")
        );

        admins = List.of(
                new User("admin0", "adminPass0", "ADMIN"),
                new User("admin1", "adminPass1", "ADMIN"),
                new User("admin2", "adminPass2", "ADMIN"),
                new User("admin3", "adminPass3", "ADMIN"),
                new User("admin4", "adminPass4", "ADMIN"),
                new User("admin5", "adminPass5", "ADMIN"),
                new User("admin6", "adminPass6", "ADMIN")
        );

        contribs = List.of(
                new User("contrib0", "contribPass0", "CONTRIBUTOR"),
                new User("contrib1", "contribPass1", "CONTRIBUTOR"),
                new User("contrib2", "contribPass2", "CONTRIBUTOR"),
                new User("contrib3", "contribPass3", "CONTRIBUTOR"),
                new User("contrib4", "contribPass4", "CONTRIBUTOR"),
                new User("contrib5", "contribPass5", "CONTRIBUTOR"),
                new User("contrib6", "contribPass6", "CONTRIBUTOR"),
                new User("contrib7", "contribPass7", "CONTRIBUTOR"),
                new User("contrib8", "contribPass8", "CONTRIBUTOR")
        );

        activities = List.of(
                new Activity("Great Activity 0", places.get(0), contribs.get(0), LocalDate.of(2020, 5, 12)),
                new Activity("Great Activity 1", places.get(0), contribs.get(0), LocalDate.of(2021, 5, 10)),
                new Activity("Great Activity 2", places.get(0), contribs.get(0), LocalDate.of(2021, 5, 2)),
                new Activity("Great Activity with really really really long description", places.get(0), contribs.get(0), LocalDate.of(2021, 4, 12)),
                new Activity("Great Activity with not so long description", places.get(0), contribs.get(0), LocalDate.of(2021, 4, 2)),
                new Activity("Activity", places.get(0), contribs.get(1), LocalDate.of(2021, 3, 10)),

                new Activity("Football", places.get(1), contribs.get(2), LocalDate.of(2021, 3, 10)),
                new Activity("Kayak", places.get(1), contribs.get(2), LocalDate.of(2021, 3, 10)),

                new Activity("Great Activity 3", places.get(2), contribs.get(3), LocalDate.of(2021, 2, 12)),

                new Activity("Great Activity 4", places.get(3), contribs.get(4), LocalDate.of(2021, 1, 12)),
                new Activity("Great Activity 5", places.get(3), contribs.get(4), LocalDate.of(2021, 1, 12)),
                new Activity("Great Activity 6", places.get(3), contribs.get(4), LocalDate.of(2021, 1, 12)),
                new Activity("Great Activity 7", places.get(3), contribs.get(5), LocalDate.of(2021, 1, 12)),

                new Activity("Great Activity 8", places.get(4), contribs.get(0), LocalDate.of(2021, 1, 12)),
                new Activity("Great Activity 9", places.get(4), contribs.get(6), LocalDate.of(2021, 1, 12))

        );

        activities.get(0).approve(admins.get(0));
        activities.get(2).approve(admins.get(0));
        activities.get(3).approve(admins.get(0));
        activities.get(4).approve(admins.get(0));
        activities.get(5).approve(admins.get(0));

        activities.get(6).approve(admins.get(1));
        activities.get(7).approve(admins.get(1));

        activities.get(8).approve(admins.get(3));

        activities.get(10).approve(admins.get(4));
        activities.get(11).approve(admins.get(4));
        activities.get(12).approve(admins.get(4));

        activities.get(14).approve(admins.get(6));


        ratings = List.of(
                new Rating(LocalDate.of(2021, 5, 12), 4, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 2, 11), 5, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 3, 23), 3, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 1, 19), 5, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 3, 31), 4, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 4, 30), 4, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2020, 9, 20), 5, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2020, 8, 1), 4, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 3, 13), 4, activities.get(0), contribs.get(0)),
                new Rating(LocalDate.of(2021, 2, 14), 3, activities.get(0), contribs.get(1)),

                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(5), contribs.get(1)),
                new Rating(LocalDate.of(2021, 6, 12), 2, activities.get(5), contribs.get(1)),
                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(5), contribs.get(2)),
                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(5), contribs.get(2)),
                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(5), contribs.get(2)),
                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(5), contribs.get(2)),

                new Rating(LocalDate.of(2021, 6, 12), 5, activities.get(6), contribs.get(3)),
                new Rating(LocalDate.of(2021, 6, 12), 4, activities.get(6), contribs.get(3)),

                new Rating(LocalDate.of(2021, 6, 12), 5, activities.get(7), contribs.get(4)),
                new Rating(LocalDate.of(2021, 6, 12), 5, activities.get(7), contribs.get(4)),
                new Rating(LocalDate.of(2021, 6, 12), 5, activities.get(7), contribs.get(4)),

                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 2, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 3, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 4, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 5, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 4, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 3, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 2, activities.get(8), contribs.get(6)),
                new Rating(LocalDate.of(2021, 6, 12), 1, activities.get(8), contribs.get(6))
        );
    }

    public static List<Place> getPlaces() {return places;}
    public static List<Activity> getActivities() {return activities;}
    public static List<Rating> getRatings() {return ratings;}
    public static List<User> getAdmins() {return admins;}
    public static List<User> getContributors() {return contribs;}
}

package com.example.tour.functional;

import com.example.tour.activity.Activity;
import com.example.tour.authentication.model.User;
import com.example.tour.place.Place;
import com.example.tour.rating.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static com.example.tour.functional.TourUtilFunctions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TourUtilFunctionsTests {

    @BeforeEach
    public void setUp() {
        MemoryBank.setUp();
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testActivitiesFromPlaces() {
        List<Place> places = MemoryBank.getPlaces();

        List<Activity> activities = activitiesFromPlaces.apply(places);
        assertEquals(activities.size(), MemoryBank.getActivities().size());
        assertTrue(activities.containsAll(MemoryBank.getActivities()));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testComparatorByListLengthDescDifferentSizes() {
        List<Activity> list1 = List.of();
        List<Activity> list2 = MemoryBank.getActivities();

        assertTrue(byListLength.compare(list1, list2) < 0);
    }

    @Test
    public void testComparatorByListLengthDescSameSize() {
        List<Activity> list1 = MemoryBank.getActivities();
        List<Activity> list2 = MemoryBank.getActivities();

        assertEquals(byListLength.compare(list1, list2), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testContributorWithMoreProposedActivities() {
        List<Place> places = MemoryBank.getPlaces();

        Optional<String> contributor = contributorWithMoreProposedActivities.apply(places);
        Assertions.assertFalse(contributor.isEmpty());
        assertEquals(contributor.get(), MemoryBank.getContributors().get(0).getUsername());
    }

    @Test
    public void testContributorWithMoreProposedActivitiesNoPlaces() {
        Optional<String> contributor = contributorWithMoreProposedActivities.apply(List.of());
        assertTrue(contributor.isEmpty());
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testApprovedByUserSuccess() {
        Activity activity = MemoryBank.getActivities().get(0);
        User admin = MemoryBank.getAdmins().get(0);
        activity.approve(admin);

        assertTrue(approvedByUser.test(activity, admin));
    }

    @Test
    public void testApprovedByUserFailure() {
        Activity activity = MemoryBank.getActivities().get(0);
        User admin = MemoryBank.getAdmins().get(0);
        activity.approve(admin);

        User admin2 = MemoryBank.getAdmins().get(1);

        Assertions.assertFalse(approvedByUser.test(activity, admin2));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testActivitiesApprovedByAdmin() {
        List<Place> places = MemoryBank.getPlaces();
        User admin = MemoryBank.getAdmins().get(0);

        int approved = admin.getApprovedActivities().size();

        assertEquals(activitiesApprovedByAdminInPlaces.apply(places, admin), approved);
    }

    @Test
    public void testActivitiesApprovedByAdminZero() {
        List<Place> places = MemoryBank.getPlaces();
        User admin = new User("thisUser", "thisPass", "ADMIN");

        assertEquals(activitiesApprovedByAdminInPlaces.apply(places, admin), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testRatedBeforeTrue() {
        Activity activity = MemoryBank.getActivities().get(0);
        LocalDate referenceDate = activity.getRatings().get(0).getDate().plusDays(1);

        assertTrue(ratedBefore.test(activity, referenceDate));
    }

    @Test
    public void testRatedBeforeFalse() {
        Activity activity = new Activity("info", MemoryBank.getPlaces().get(0));
        new Rating(LocalDate.of(2021, 5, 23), 2, activity);

        LocalDate referenceDate = activity.getRatings().get(0).getDate().minusDays(1);

        Assertions.assertFalse(ratedBefore.test(activity, referenceDate));
    }

    @Test
    public void testRatedBeforeNoRatings() {
        Activity activity = new Activity("info", MemoryBank.getPlaces().get(0));

        LocalDate referenceDate = LocalDate.of(2021, 5, 23);

        Assertions.assertFalse(ratedBefore.test(activity, referenceDate));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testActivitiesRatedBefore() {
        List<Place> places = MemoryBank.getPlaces();
        LocalDate referenceDate = LocalDate.of(2021, 6, 12);

        List<Activity> activities = activitiesRatedBefore.apply(places, referenceDate);

        List<Rating> ratings = MemoryBank.getRatings();
        ArrayList<Activity> checkList = new ArrayList<>();
        for (Rating rating: ratings) {
            if (rating.getDate().isBefore(referenceDate) && !checkList.contains(rating.getActivity())) {
                checkList.add(rating.getActivity());
            }
        }

        assertEquals(activities.size(), checkList.size());
        assertTrue(activities.containsAll(checkList));
    }

    @Test
    public void testActivitiesRatedBeforeNone() {
        List<Place> places = MemoryBank.getPlaces();
        LocalDate referenceDate = LocalDate.of(1930, 6, 12);

        List<Activity> activities = activitiesRatedBefore.apply(places, referenceDate);

        assertEquals(activities.size(), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testRatingsFromContrib() {
        User contributor = MemoryBank.getContributors().get(0);

        List<Rating> ratingsFromMethod = contributor.getRatings();
        List<Rating> ratingsFromFunction = ratingsFromContrib.apply(contributor);

        assertEquals(ratingsFromFunction.size(), ratingsFromMethod.size());
        assertTrue(ratingsFromFunction.containsAll(ratingsFromMethod));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testScoreHigherThanHigher() {
        Rating r = new Rating(LocalDate.now(), 5, MemoryBank.getActivities().get(0));
        assertTrue(scoreHigherThan.test(r, 4));
    }

    @Test
    public void testScoreHigherThanEqual() {
        Rating r = new Rating(LocalDate.now(), 4, MemoryBank.getActivities().get(0));
        Assertions.assertFalse(scoreHigherThan.test(r, 4));
    }

    @Test
    public void testScoreHigherThanLower() {
        Rating r = new Rating(LocalDate.now(), 3, MemoryBank.getActivities().get(0));
        Assertions.assertFalse(scoreHigherThan.test(r, 4));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testIsInSouthHemisphereSouth() {
        Place p = new Place("Southern", -150, 32, "Place in southern hem.");
        assertTrue(isInSouthHemisphere.test(p));
    }

    @Test
    public void testIsInSouthHemisphereNorth() {
        Place p = new Place("Southern", 150, 32, "Place in southern hem.");
        Assertions.assertFalse(isInSouthHemisphere.test(p));
    }

    @Test
    public void testIsInSouthHemisphereEquator() {
        Place p = new Place("Southern", 0, 32, "Place in southern hem.");
        Assertions.assertFalse(isInSouthHemisphere.test(p));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testPlaceNamesWithContribHighRatingsInSouthHemisphere() {
        User contributor = MemoryBank.getContributors().get(3);
        Integer baseRating = 3;
        List<String> places = placeNamesWithContribHighRatingsInSouthHemisphere.apply(contributor, baseRating);

        List<Rating> ratings = contributor.getRatings();

        HashSet<String> checkList = new HashSet<>();
        for (Rating r: ratings) {
            Place p = r.getActivity().getPlace();
            if (p.getLatitude() < 0) {
                checkList.add(p.getName());
            }
        }

        assertEquals(places.size(), checkList.size());
        assertTrue(places.containsAll(checkList));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testPlacesWithMoreThanKActivities() {
        List<Place> places = MemoryBank.getPlaces();
        int k = 3;

        List<String> result = placesWithMoreThanKActivities.apply(places, k);

        HashSet<String> compareSet = new HashSet<>();
        for (Place p: places) {
            int count = 0;
            List<Activity> acts = p.getActivities();
            for (Activity a: acts) {
                if (a.isActive()) count++;
            }
            if (count > k) {
                compareSet.add(p.getName());
            }
        }

        assertEquals(result.size(), compareSet.size());
        assertTrue(result.containsAll(compareSet));
    }

    /**
     * --------------------------------------
     */
    private Double getCompareDouble(List<Activity> activities) {
        double compareDouble = 0.0;
        for (Activity a: activities) {
            compareDouble += getCompareDouble(a);
        }
        if (activities.size() > 0) {
            compareDouble /= activities.size();
        }

        return compareDouble;
    }
    private Double getCompareDouble(Activity activity) {
        double compareDouble = 0.0;
        for (Rating r: activity.getRatings()) {
            compareDouble += r.getScore();
        }
        if (activity.getRatings().size() > 0) {
            compareDouble /= activity.getRatings().size();
        }

        return compareDouble;
    }

    private Double getCompareDouble(Place place) {
        Double compareDouble = 0.0;
        int count = 0;
        for (Activity a: place.getActivities()) {
            if (a.isActive()) {
                compareDouble += getCompareDouble(a);
                count++;
            }
        }
        if (count > 0) {
            compareDouble /= count;
        }

        return compareDouble;
    }

    @Test
    public void testAverageRating() {
        Activity activity = MemoryBank.getActivities().get(0);
        Double result = averageRating.applyAsDouble(activity);

        Double compareDouble = getCompareDouble(activity);

        assertEquals(result, compareDouble);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testAverageRatingGreaterThanGreater() {
        Activity a = MemoryBank.getActivities().get(0);
        Double compareDouble = getCompareDouble(a) - 1;

        assertTrue(averageRatingGreaterThanK.test(a, compareDouble));
    }

    @Test
    public void testAverageRatingGreaterThanEqual() {
        Activity a = MemoryBank.getActivities().get(0);
        Double compareDouble = getCompareDouble(a);

        Assertions.assertFalse(averageRatingGreaterThanK.test(a, compareDouble));
    }

    @Test
    public void testAverageRatingGreaterThanLower() {
        Activity a = MemoryBank.getActivities().get(0);
        Double compareDouble = getCompareDouble(a) + 1;

        Assertions.assertFalse(averageRatingGreaterThanK.test(a, compareDouble));
    }


    /**
     * --------------------------------------
     */
    @Test
    public void testAverageRatingActiveActivitiesInPlace() {
        Place place = MemoryBank.getPlaces().get(2);

        Double result = averageRatingActiveActivitiesInPlace.apply(place).getAsDouble();
        Double compareDouble = getCompareDouble(place);

        assertEquals(result, compareDouble);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testActivitiesWithAverageRatingGreaterThanK() {
        Place place = MemoryBank.getPlaces().get(0);
        double k = 3.0;

        List<Activity> result = activitiesWithAverageRatingGreaterThanK.apply(place, k);

        HashSet<Activity> compareSet = new HashSet<>();
        for (Activity a: place.getActivities()) {
            if (getCompareDouble(a) > k) {
                compareSet.add(a);
            }
        }

        assertEquals(result.size(), compareSet.size());
        assertTrue(result.containsAll(compareSet));
    }

    /**
     * --------------------------------------
     */

    @Test
    public void testActiveActivitiesInPlace() {
        Place place = MemoryBank.getPlaces().get(0);
        List<Activity> actual = List.of(
                MemoryBank.getActivities().get(0),
                MemoryBank.getActivities().get(2),
                MemoryBank.getActivities().get(3),
                MemoryBank.getActivities().get(4),
                MemoryBank.getActivities().get(5)
        );
        List<Activity> result = activeActivitiesFromPlace.apply(place);

        assertEquals(result.size(), actual.size());
        assertTrue(result.containsAll(actual));
    }
    @Test
    public void testAverageRatingActiveActivities() {
        Place place = MemoryBank.getPlaces().get(1);
        List<Activity> activities = activeActivitiesFromPlace.apply(place);

        Double result = averageRatingOfActivities.applyAsDouble(activities);
        Double actual = getCompareDouble(activities);

        assertEquals(result, actual);
    }

    @Test
    public void testTopKActivitiesNearMe() {
        List<Place> places = MemoryBank.getPlaces();
        double lat = 41.01;
        double lon = 92.01;
        double radius = 10.0;
        int k = 2;

        List<String> result = topKActivitiesNearMe.apply(places, radius, lat, lon, k);

        assertEquals(result.size(), k);
        assertTrue(result.containsAll(List.of("Great Activity 0", "Activity")));
    }

    @Test
    public void testTop5ActivitiesNearMe() {
        List<Place> places = MemoryBank.getPlaces();
        double lat = 41.01;
        double lon = 92.01;
        double radius = 10.0;

        List<String> result = top5ActivitiesNearMe.apply(places, radius, lat, lon);
        List<String> actual = List.of(
                MemoryBank.getActivities().get(0).getInfo(),
                MemoryBank.getActivities().get(5).getInfo(),
                MemoryBank.getActivities().get(2).getInfo(),
                MemoryBank.getActivities().get(3).getInfo(),
                MemoryBank.getActivities().get(4).getInfo()
        );
        assertEquals(result.size(), actual.size());
        assertTrue(result.containsAll(actual));
        for (int i = 0; i< result.size() -1; i++){
            assertTrue(result.get(i).equals(actual.get(i)));
        }
    }

    @Test
    public void testTop5ActivitiesIn10kmRadius() {
        List<Place> places = MemoryBank.getPlaces();
        double lat = 41.01;
        double lon = 92.01;

        List<String> result = top5ActivitiesInside10KmRadius.apply(places, lat, lon);
        List<String> actual = List.of(
                MemoryBank.getActivities().get(0).getInfo(),
                MemoryBank.getActivities().get(5).getInfo(),
                MemoryBank.getActivities().get(2).getInfo(),
                MemoryBank.getActivities().get(3).getInfo(),
                MemoryBank.getActivities().get(4).getInfo()
        );
        assertEquals(result.size(), actual.size());
        assertTrue(result.containsAll(actual));
    }

    @Test
    public void testWorstAdmin() {
        List<Place> places = MemoryBank.getPlaces();
        String actual = MemoryBank.getAdmins().get(0).getUsername();
        String result = worstAdmin.apply(places);

        assertEquals(result, actual);
    }

    @Test
    public void testMostRecentAndHighlyRatedKActivities() {
        List<Place> places = MemoryBank.getPlaces();
        int k = 5;
        List<String> actual = List.of(
                MemoryBank.getActivities().get(2).getInfo(),
                MemoryBank.getActivities().get(3).getInfo(),
                MemoryBank.getActivities().get(4).getInfo(),
                MemoryBank.getActivities().get(6).getInfo(),
                MemoryBank.getActivities().get(7).getInfo()
        );

        List<String> result = mostRecentAndHighlyRatedKActivities.apply(places, k);

        assertEquals(result.size(), actual.size());
        assertTrue(result.containsAll(actual));
    }

    @Test
    public void testMostRecentAndHighlyRated5Activities() {
        List<Place> places = MemoryBank.getPlaces();
        List<String> actual = List.of(
                MemoryBank.getActivities().get(2).getInfo(),
                MemoryBank.getActivities().get(3).getInfo(),
                MemoryBank.getActivities().get(4).getInfo(),
                MemoryBank.getActivities().get(6).getInfo(),
                MemoryBank.getActivities().get(7).getInfo()
        );

        List<String> result = mostRecentAndHighlyRated5Activities.apply(places);

        assertEquals(result.size(), actual.size());
        assertTrue(result.containsAll(actual));
    }
    /**
     * --------------------------------------
     */
    @Test
    public void testStarUser() {
        List<Place> places = MemoryBank.getPlaces();
        List<String> result = starUsers.apply(places, LocalDate.of(2021, 5, 13));
        assertEquals(result.size(), 1);
        assertTrue(result.contains("contrib0"));
    }
    @Test
    public void testPercentage() {
        Place place = MemoryBank.getPlaces().get(0);
        Double result = percentageOfNotApprovedActivities.apply(place);
        assertEquals(result, 1/6.0*100);
    }
    @Test
    public void testMostActiveYear() {
        List<Place> places = MemoryBank.getPlaces();
        Long result = mostActiveYear.apply(places);
        assertEquals(result, 2021);
    }
    @Test
    public void testMostActiveYearByActivities(){
        List<Place> places = MemoryBank.getPlaces();
        Long result = mostActiveYearByActivitiesUserSpecific.apply(places,"contrib0");
        assertEquals(result, 2021);
    }
    @Test
    void test_mostRatedActivityInGivenAdmin() {
        List<Place> places = MemoryBank.getPlaces();
        assertEquals(2, topRatedActivityByAdmin.apply(places, "admin0", 2).size());
    }

    @Test
    void test_mostActivePlaceInAYear() {
        List<Place> places = MemoryBank.getPlaces();
        assertTrue(mostActivePlaceInAYear.apply(places, 2021, 3).isPresent());
    }

    @Test
    void test_contributorWhoProposedTopActivities() {
        List<Place> places = MemoryBank.getPlaces();
        assertTrue(contributorWhoProposedTopActivities.apply(places, 2021).isPresent());
        assertEquals("CONTRIBUTOR", contributorWhoProposedTopActivities.apply(places, 2021).get().getRole());
    }


}

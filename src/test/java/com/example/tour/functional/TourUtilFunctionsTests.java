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
        Assertions.assertEquals(activities.size(), MemoryBank.getActivities().size());
        Assertions.assertTrue(activities.containsAll(MemoryBank.getActivities()));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testComparatorByListLengthDescDifferentSizes() {
        List<Activity> list1 = List.of();
        List<Activity> list2 = MemoryBank.getActivities();

        Assertions.assertTrue(byListLength.compare(list1, list2) < 0);
    }

    @Test
    public void testComparatorByListLengthDescSameSize() {
        List<Activity> list1 = MemoryBank.getActivities();
        List<Activity> list2 = MemoryBank.getActivities();

        Assertions.assertEquals(byListLength.compare(list1, list2), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testContributorWithMoreProposedActivities() {
        List<Place> places = MemoryBank.getPlaces();

        Optional<String> contributor = contributorWithMoreProposedActivities.apply(places);
        Assertions.assertFalse(contributor.isEmpty());
        Assertions.assertEquals(contributor.get(), MemoryBank.getContributors().get(0).getUsername());
    }

    @Test
    public void testContributorWithMoreProposedActivitiesNoPlaces() {
        Optional<String> contributor = contributorWithMoreProposedActivities.apply(List.of());
        Assertions.assertTrue(contributor.isEmpty());
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testApprovedByUserSuccess() {
        Activity activity = MemoryBank.getActivities().get(0);
        User admin = MemoryBank.getAdmins().get(0);
        activity.approve(admin);

        Assertions.assertTrue(approvedByUser.test(activity, admin));
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

        Assertions.assertEquals(activitiesApprovedByAdminInPlaces.apply(places, admin), approved);
    }

    @Test
    public void testActivitiesApprovedByAdminZero() {
        List<Place> places = MemoryBank.getPlaces();
        User admin = new User("thisUser", "thisPass", "ADMIN");

        Assertions.assertEquals(activitiesApprovedByAdminInPlaces.apply(places, admin), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testRatedBeforeTrue() {
        Activity activity = MemoryBank.getActivities().get(0);
        LocalDate referenceDate = activity.getRatings().get(0).getDate().plusDays(1);

        Assertions.assertTrue(ratedBefore.test(activity, referenceDate));
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

        Assertions.assertEquals(activities.size(), checkList.size());
        Assertions.assertTrue(activities.containsAll(checkList));
    }

    @Test
    public void testActivitiesRatedBeforeNone() {
        List<Place> places = MemoryBank.getPlaces();
        LocalDate referenceDate = LocalDate.of(1930, 6, 12);

        List<Activity> activities = activitiesRatedBefore.apply(places, referenceDate);

        Assertions.assertEquals(activities.size(), 0);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testRatingsFromContrib() {
        User contributor = MemoryBank.getContributors().get(0);

        List<Rating> ratingsFromMethod = contributor.getRatings();
        List<Rating> ratingsFromFunction = ratingsFromContrib.apply(contributor);

        Assertions.assertEquals(ratingsFromFunction.size(), ratingsFromMethod.size());
        Assertions.assertTrue(ratingsFromFunction.containsAll(ratingsFromMethod));
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testScoreHigherThanHigher() {
        Rating r = new Rating(LocalDate.now(), 5, MemoryBank.getActivities().get(0));
        Assertions.assertTrue(scoreHigherThan.test(r, 4));
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
        Assertions.assertTrue(isInSouthHemisphere.test(p));
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

        Assertions.assertEquals(places.size(), checkList.size());
        Assertions.assertTrue(places.containsAll(checkList));
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

        Assertions.assertEquals(result.size(), compareSet.size());
        Assertions.assertTrue(result.containsAll(compareSet));
    }

    /**
     * --------------------------------------
     */

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

        Assertions.assertEquals(result, compareDouble);
    }

    /**
     * --------------------------------------
     */
    @Test
    public void testAverageRatingGreaterThanGreater() {
        Activity a = MemoryBank.getActivities().get(0);
        Double compareDouble = getCompareDouble(a) - 1;

        Assertions.assertTrue(averageRatingGreaterThanK.test(a, compareDouble));
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

        Assertions.assertEquals(result, compareDouble);
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

        Assertions.assertEquals(result.size(), compareSet.size());
        Assertions.assertTrue(result.containsAll(compareSet));
    }
}

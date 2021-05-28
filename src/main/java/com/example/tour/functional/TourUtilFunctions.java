package com.example.tour.functional;

import com.example.tour.activity.Activity;
import com.example.tour.authentication.model.User;
import com.example.tour.place.Place;
import com.example.tour.rating.Rating;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public class TourUtilFunctions {

    /**
     * @return Activity which is most active in a year for a given admin
     */

    public static final Function<List<Rating>, Integer> sumUp = rates -> rates.stream().map(Rating::getScore).reduce(0, Integer::sum);
    public static final TriFunction<List<Place>, String, Integer, List<Activity>>
            topRatedActivityByAdmin = (places, user, k) ->
            Optional.ofNullable(places)
                    .orElse(List.of())
                    .stream()
                    .flatMap(place -> place.getActivities().stream())
                    .filter(Activity::isActive)
                    .filter(activity -> activity.getApprovedBy().getUsername().equals(user))
                    .sorted((a1, a2) -> sumUp.apply(a2.getRatings()) - sumUp.apply(a1.getRatings()))
                    .limit(k)
                    .collect(toList());

    /**
     * @return Place which has top number of activities in a given year
     */
    public static final TriFunction<List<Place>, Integer, Integer, Optional<Place>>
            mostActivePlaceInAYear = (places, year, k) ->
            Optional.ofNullable(places)
                    .orElse(List.of())
                    .stream()
                    .filter(place -> place.getActivities().size() >= k)
                    .flatMap(place -> place.getActivities().stream())
                    .filter(activity -> activity.getCreatedDate().getYear() == year)
                    .sorted((a1, a2) -> sumUp.apply(a2.getRatings()) - sumUp.apply(a1.getRatings()))
                    .map(Activity::getPlace)
                    .findFirst();
    /**
     * @return Contributor who proposed top rated activities
     */
    public static BiFunction<List<Place>, Integer, Optional<User>>
            contributorWhoProposedTopActivities = (places, year) ->
            Optional.ofNullable(places)
                    .orElse(List.of())
                    .stream()
                    .flatMap(place -> place.getActivities().stream())
                    .filter(activity -> activity.getCreatedDate().getYear() == year)
                    .sorted((a1, a2) -> sumUp.apply(a2.getRatings()) - sumUp.apply(a1.getRatings()))
                    .limit(1)
                    .map(Activity::getProposedBy)
                    .findFirst();
    
    public static final Comparator<List<Activity>> byListLength = (l1, l2) -> l1.size() - l2.size();
    public static final Function<List<Place>, List<Activity>> activitiesFromPlaces = places ->
            places.stream()
                .flatMap(p -> p.getActivities().stream())
                .collect(Collectors.toList());
    public static final Function<List<Place>, List<Activity>> activeActivitiesFromPlaces = places ->
            activitiesFromPlaces.apply(places).stream()
                .filter(Activity::isActive)
                .collect(Collectors.toList());

    public static final Function<User, List<Rating>> ratingsFromContrib =
            contrib -> List.of(contrib).stream()
                    .flatMap(c -> c.getRatings().stream())
                    .collect(Collectors.toList());

    /**
     * --------------------------------------
     */
    public static final Function<List<Place>, Optional<String>> contributorWithMoreProposedActivities = places ->
            activitiesFromPlaces.apply(places).stream()
                .collect(Collectors.groupingBy(a -> a.getProposedBy())).entrySet().stream()
                .max((e1, e2) -> byListLength.compare(e1.getValue(), e2.getValue()))
                .map(entry -> entry.getKey().getUsername());


    public static final BiPredicate<Activity, User> approvedByUser = (activity, user) ->
            activity.getApprovedBy() != null && activity.getApprovedBy().equals(user);
    public static final BiFunction<List<Place>, User, Long> activitiesApprovedByAdminInPlaces = (places, admin) ->
            activitiesFromPlaces.apply(places).stream()
                .filter(a -> approvedByUser.test(a, admin))
                .count();

    public static final BiPredicate<Activity, LocalDate> ratedBefore = (activity, date) ->
            List.of(activity).stream()
                .flatMap(a -> a.getRatings().stream())
                .anyMatch(r -> r.getDate().isBefore(date));
    public static final BiFunction<List<Place>, LocalDate, List<Activity>> activitiesRatedBefore = (places, date) ->
            activitiesFromPlaces.apply(places).stream()
                .filter(a -> ratedBefore.test(a, date))
                .collect(Collectors.toList());

    public static final BiPredicate<Rating, Integer> scoreHigherThan = (r, baseScore) -> r.getScore() > baseScore;
    public static final Predicate<Place> isInSouthHemisphere = p -> p.getLatitude() < 0;
    public static final BiFunction<User, Integer, List<String>> placeNamesWithContribHighRatingsInSouthHemisphere =
            (contrib, baseScore) -> ratingsFromContrib.apply(contrib).stream()
                .filter(r -> scoreHigherThan.test(r, baseScore))
                .map(r -> r.getActivity())
                .map(activity -> activity.getPlace())
                .distinct()
                .filter(isInSouthHemisphere)
                .map(p -> p.getName())
                .collect(Collectors.toList());

    /**
     * --------------------------------------
     */
    public static final BiFunction<List<Place>, Integer, List<String>> placesWithMoreThanKActivities =
            (places, k) -> activeActivitiesFromPlaces.apply(places).stream()
                .collect(Collectors.groupingBy(a -> a.getPlace(), Collectors.counting())).entrySet().stream()
                .filter(entry -> entry.getValue() > k)
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());

    public static final ToDoubleFunction<Activity> averageRating =
            activity -> List.of(activity).stream()
                .flatMap(a -> a.getRatings().stream())
                .mapToInt(r -> r.getScore())
                .average().orElse(0.0);
    public static final Function<Place, OptionalDouble> averageRatingActiveActivitiesInPlace =
            place -> activeActivitiesFromPlaces.apply(List.of(place)).stream()
                .mapToDouble(averageRating)
                .average();

    public static final BiPredicate<Activity, Double> averageRatingGreaterThanK =
            (activity, k) -> averageRating.applyAsDouble(activity) > k;
    public static final BiFunction<Place, Double, List<Activity>> activitiesWithAverageRatingGreaterThanK =
            (place, k) -> activeActivitiesFromPlaces.apply(List.of(place)).stream()
                .filter(a -> averageRatingGreaterThanK.test(a, k))
                .collect(Collectors.toList());

    /**
     * --------------------------------------
     */

    public static final QuadPredicate<Place, Double, Double,  Double> placeInsideAnAreaOfRadius =
            (place, radius, lat, lon) ->
                    Math.pow((place.getLatitude() - lat)*111, 2) + Math.pow((place.getLatitude() - lat)*111, 2) <= radius;

    public static final Function<Place, List<Activity>> activeActivitiesFromPlace = place ->
            place.getActivities().stream()
                    .filter(Activity::isActive)
                    .collect(Collectors.toList());

    public static final PentFunction<List<Place>, Double, Double, Double, Integer, List<String>> topKActivitiesNearMe =
        (places, radius, lat, lon, k) -> places.stream()
            .filter(p -> placeInsideAnAreaOfRadius.test(p, radius, lat, lon))
            .flatMap(p -> activeActivitiesFromPlace.apply(p).stream())
            .sorted(Comparator.comparingDouble(averageRating::applyAsDouble).reversed())
            .limit(k)
            .map(a -> a.getInfo())
            .collect(Collectors.toList());

    public static final QuadFunction<List<Place>, Double, Double, Double, List<String>> top5ActivitiesNearMe =
            (places, radius, lat, lon) -> topKActivitiesNearMe.apply(places, radius, lat, lon, 5);

    public static final TriFunction<List<Place>, Double, Double, List<String>> top5ActivitiesInside10KmRadius =
            (places, lat, lon) -> top5ActivitiesNearMe.apply(places, 10.0, lat, lon);

    public static final ToDoubleFunction<List<Activity>> averageRatingOfActivities =
            activities -> activities.stream()
                    .mapToDouble(averageRating)
                    .average()
                    .orElse(0.0);

    public static final Comparator<List<Activity>> byAverageRating =
            Comparator.comparing(al -> averageRatingOfActivities.applyAsDouble(al));

    public static final Function<List<Place>, String> worstAdmin = places ->
            activeActivitiesFromPlaces.apply(places).stream()
                    .filter(a -> a.getRatings().size() > 0)
                    .collect(Collectors.groupingBy(a -> a.getApprovedBy())).entrySet().stream()
                    .min((e1,e2) -> byAverageRating.compare(e1.getValue(), e2.getValue()))
                    .map(entry -> entry.getKey().getUsername())
                    .orElse(null);

    public static final BiFunction<List<Place>, Integer, List<String>> mostRecentAndHighlyRatedKActivities = (places, k) ->
            activeActivitiesFromPlaces.apply(places).stream()
            .sorted(Comparator.comparing(Activity::getCreatedDate).reversed()
                    .thenComparing(Comparator.comparingDouble(averageRating::applyAsDouble).reversed()))
            .limit(k)
            .map(a -> a.getInfo())
            .collect(Collectors.toList());

    public static final Function<List<Place>, List<String>> mostRecentAndHighlyRated5Activities = (places) ->
            mostRecentAndHighlyRatedKActivities.apply(places, 5);
    /**
     * --------------------------------------
     */

    public static final BiFunction<List<Place>, LocalDate, List<String>> starUsers = (places, date) ->activeActivitiesFromPlaces.apply(places).stream()
            .filter(y -> ratedBefore.test(y,date))
            .filter(a -> averageRatingGreaterThanK.test(a, 4D))
            .map(u->u.getProposedBy().getUsername())
            .collect(Collectors.toList());

    public static final Function<Place, Long> countOfNotApprovedActivities = place -> activitiesFromPlaces.apply(List.of(place)).stream()
            .filter(r->r.isActive()==false)
            .count();
    public static final Function<Place, Long> countOfAllActivities = place -> activitiesFromPlaces.apply(List.of(place)).stream()
            .count();
    public static final Function<Place, Double> percentageOfNotApprovedActivities = place -> (countOfNotApprovedActivities.apply(place).doubleValue()
            /countOfAllActivities.apply(place).doubleValue()) * 100;

    public static final Function<List<Place>, Long> mostActiveYear = places -> activeActivitiesFromPlaces.apply(places).stream()
            .flatMap(a->a.getRatings().stream())
            .collect(Collectors.groupingBy(r->r.getDate().getYear())).entrySet().stream()
            .max(Comparator.comparingInt(r->r.getValue().size()))
            .orElse(null).getKey().longValue();

    public static final BiFunction<List<Place>, String, Long> mostActiveYearByActivitiesUserSpecific = (places, user) -> activeActivitiesFromPlaces.apply(places).stream()
            .filter(u->u.getProposedBy().getUsername()==user)
            .collect(Collectors.groupingBy(r->r.getCreatedDate().getYear())).entrySet().stream()
            .max(Comparator.comparingInt(r->r.getValue().size()))
            .orElse(null).getKey().longValue();


}

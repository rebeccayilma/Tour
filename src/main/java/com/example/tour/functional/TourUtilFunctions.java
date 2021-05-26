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

public class TourUtilFunctions {

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

    public static final BiFunction<List<Place>, LocalDate, List<String>> starUsers = (places, date) -> activeActivitiesFromPlaces.apply(places).stream()
            .filter(y -> ratedBefore.test(y,date))
            .filter(a -> averageRatingGreaterThanK.test(a, 4D))
            .map(u->u.getProposedBy().getUsername())
            .collect(Collectors.toList());

    public static final Function<Place, Long> countOfNotApprovedActivities = place -> activitiesFromPlaces.apply(List.of(place)).stream()
            .filter(r->r.isActive()==false)
            .count();
    public static final Function<Place, Long> countOfAllActivities = place -> activitiesFromPlaces.apply(List.of(place)).stream()
            .count();
    public static final Function<Place, Double> percentageOfNotApprovedActivities = place -> countOfNotApprovedActivities.apply(place).doubleValue()
            /countOfAllActivities.apply(place).doubleValue() * 100;

}


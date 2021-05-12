package com.example.tour.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByActivityId(Long activityId);
    Optional<Rating> findByIdAndActivityId(Long id, Long activityId);
}

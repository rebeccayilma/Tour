package com.example.tour.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT u FROM rating u WHERE u.activity = ?1")
    List<Rating> findAllByActivity_Id(Long activity_id);
}

package com.example.tour.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("SELECT p FROM Place p WHERE p.name = ?1")
    Optional<Place> findPlaceByName(String name);
    @Query("SELECT p FROM Place p WHERE p.name  = ?1 AND p.description = ?2")
    List<Place> findPlaceByNameEqualsAndDescriptionEquals(String name, String description);
}

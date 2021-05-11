package com.TourSystem.toursystem.Activity;

import com.TourSystem.toursystem.models.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    List<Activity> findAllByActive(Boolean active);

    String getByInformation(String information);
}

package com.example.tour.activity;

import com.example.tour.place.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Activity {
    @Id
    @SequenceGenerator(
            name = "activity_sequence",
            sequenceName = "activity_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activity_sequence"
    )

    private String info;
    private boolean isActive;
    @ManyToOne
    private Place place;

    public Activity(String info, Place place) {
        this.info = info;
        this.place = place;

        // Created by Contributor; needs to be approved by Admin
        this.isActive = false;
    }

    public String getInfo() {
        return info;
    }

    public boolean isActive() {
        return isActive;
    }

    public Place getPlace() {
        return place;
    }
}

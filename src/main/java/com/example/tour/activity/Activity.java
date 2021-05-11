package com.example.tour.activity;

import com.example.tour.place.Place;

import javax.persistence.*;

@Entity(name = "activity")
@Table(name = "activity")
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

    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "info", columnDefinition = "TEXT", nullable = false)
    private String info;
    @Column(name = "isActive", columnDefinition = "boolean default false")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "place_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Place place;

    public Activity(String info, Place place) {
        this.info = info;
        this.place = place;
        place.addActivity(this);

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

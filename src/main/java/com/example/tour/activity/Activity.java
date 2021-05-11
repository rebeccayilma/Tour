package com.example.tour.activity;

import com.example.tour.place.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Activity {
    @SequenceGenerator(
            name = "activity_sequence",
            sequenceName = "activity_sequence",
            allocationSize = 1
    )
    @Id
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
    @ManyToOne (
    cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "place_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "place_activity_fk"
            )
    )
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

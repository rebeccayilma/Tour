package com.example.tour.place;

import com.example.tour.activity.Activity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Place")
@Table(
        name = "place",
        uniqueConstraints = {@UniqueConstraint(name="place_name_unique", columnNames = "name")}
)
public class Place {
    @Id
    @SequenceGenerator(
            name = "place_sequence",
            sequenceName = "place_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "place_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "description")
    private String description;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "place",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    )
    private List<Activity> activities = new ArrayList<>();

    public Place(String name, double latitude, double longitude, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        //this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

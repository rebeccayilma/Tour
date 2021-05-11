package com.example.tour.place;

import com.example.tour.activity.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "place")
@Table(name = "place",
        uniqueConstraints = {@UniqueConstraint(name = "place_name_unique", columnNames = "name")}

)
public class Place {
    @Id
    @SequenceGenerator(name = "place_sequence",
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
    //Here it will depend on the Activity model
    //TO DO

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "place_id",
            referencedColumnName = "id"
    )
    private List<Activity> activities;

    public Place(String name, double latitude, double longitude, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }
}

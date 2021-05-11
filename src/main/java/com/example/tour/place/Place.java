package com.example.tour.place;

import com.example.tour.activity.Activity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="place")
@Table(name = "place",
uniqueConstraints = {@UniqueConstraint(name="place_name_uniqe", columnNames = "name")}

)
public class Place {
    @Id
    @SequenceGenerator(name = "place_sequence",
    sequenceName = "place_sequence",
    allocationSize = 1)

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

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Activity> activities;
}

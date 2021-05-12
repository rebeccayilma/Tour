package com.example.tour.activity;

import com.example.tour.place.Place;
import com.example.tour.rating.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "activity")
@Table(name = "activity")
@NoArgsConstructor
@Data
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
    @ManyToOne (
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )

    @JoinColumn(
            name = "place_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "place_activity_fk"
            )
    )
    private Place place;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "activity",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Rating> rating = new ArrayList<>();

    public Activity(String info, Place place) {
        this.info = info;
        this.place = place;
        place.addActivity(this);

        // Created by Contributor; needs to be approved by Admin
        this.isActive = false;
    }
}

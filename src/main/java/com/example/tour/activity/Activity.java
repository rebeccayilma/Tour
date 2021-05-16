package com.example.tour.activity;

import com.example.tour.place.Place;
import com.example.tour.rating.Rating;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonBackReference
    private Place place;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "activity",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Rating> ratings = new ArrayList<>();
    @Column(name = "image_path", columnDefinition = "TEXT")
    private String imagePath;

    public void addRating(Rating rating){
            this.ratings.add(rating);

    }
    @JsonManagedReference
    public List<Rating> getRatings() {
        return ratings;
    }

    public Activity(String info, Place place) {
        if (info == null) throw new IllegalArgumentException("Info of activity must not be null");
        if (place == null) throw new IllegalArgumentException("Place where activity is located must not be null");

        this.info = info;
        this.place = place;
        place.addActivity(this);

        // Created by Contributor; needs to be approved by Admin
        this.isActive = false;
    }
}

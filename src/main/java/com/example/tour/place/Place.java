package com.example.tour.place;

import com.example.tour.TransformerUtils;
import com.example.tour.activity.Activity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Getter
@Setter
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
    @Column(name = "name", nullable = false)
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
    @JsonManagedReference
    private List<Activity> activities = new ArrayList<>();
    @OneToMany(
            mappedBy = "place",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    )
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();

    public Place(String name, double latitude, double longitude, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public String toString() {
        return TransformerUtils.createPlaceDTO(this).toString();
    }
}

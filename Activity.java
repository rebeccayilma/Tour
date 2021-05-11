package com.TourSystem.toursystem.Activity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Activity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String information;
    private boolean active;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "activity")
    @LazyCollection(LazyCollectionOption.FALSE)

   private List<Rating> rating;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;


    public Activity() {
    }

    public Activity(String information, List<Rating> rating, Place place) {

        this.information = information;
        this.active = false;
        this.rating = rating;
        this.place = place;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +

                ", description='" + information + '\'' +
                ", active=" + active +
                ", rating=" + rating +
                ", place=" + place +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return information;
    }

    public void setDescription(String description) {
        this.information = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}

package com.example.tour.authentication.model;

import com.example.tour.activity.Activity;
import com.example.tour.rating.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @Pattern(regexp = "^(ADMIN|CONTRIBUTOR)$")
    private String role;

    private List<Rating> ratings;
    private List<Activity> proposedActivities;
    private List<Activity> approvedActivities;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.ratings = new ArrayList<>();
        this.proposedActivities = new ArrayList<>();
        this.approvedActivities = new ArrayList<>();
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }

    public void addProposedActivity(Activity a) {
        proposedActivities.add(a);
    }

    public void addApprovedActivity(Activity a) {
        approvedActivities.add(a);
    }

    public String toString() {
        return
            "[Id: " + id + ", username: " + username + ", role: " + role + "]";
    }
}
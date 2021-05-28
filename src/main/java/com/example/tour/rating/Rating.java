package com.example.tour.rating;

import com.example.tour.activity.Activity;
import com.example.tour.authentication.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@Data
@Entity(name = "rating")
@Table(name = "rating")

public class Rating {
    @Id
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "score", updatable = false, nullable = false)
    private int score;

    @ManyToOne (
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL

    )

    @JoinColumn(
            name = "activity_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "rating_activity_fk"
            )

    )
    @JsonBackReference
    private Activity activity;

    private User user;

    public Rating(LocalDate date, int score, Activity activity) {
        this.date = date;
        this.score = score;
        this.activity = activity;
        activity.addRating(this);
    }

    public Rating(LocalDate date, int score, Activity activity, User user) {
        this(date, score, activity);
        this.user = user;
        user.addRating(this);
    }
}

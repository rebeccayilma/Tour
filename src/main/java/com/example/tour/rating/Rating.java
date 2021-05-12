package com.example.tour.rating;

import com.example.tour.activity.Activity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
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
            generator = "activity_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    private LocalDate date;
    private int score;

    @ManyToOne (
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )

    @JoinColumn(
            name = "activity_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "rating_activity_fk"
            )

    )
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


//private Person person;

    public Rating(LocalDate date, int score, Activity activity) {
        this.date = date;
        this.score = score;
        this.activity = activity;
    }


}

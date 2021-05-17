package com.example.tour.place;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "image")
@Table(name = "image")
public class Image {
    @Id
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "path", columnDefinition = "TEXT")
    private String path;
    @ManyToOne ()
    @JoinColumn(
            name = "place_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "place_image_fk"
            )
    )
    @JsonBackReference
    private Place place;
}

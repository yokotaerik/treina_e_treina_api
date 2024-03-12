package com.yokota.treino.model.set;

import com.yokota.treino.model.workout.Workout;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "set_table")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private double weight;
    private double reps;
    private double minutesResting;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    private Workout workout;
}
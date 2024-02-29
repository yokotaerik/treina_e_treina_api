package com.yokota.treino.model.set;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.workout.Workout;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @OneToOne
    private Workout workout;
}
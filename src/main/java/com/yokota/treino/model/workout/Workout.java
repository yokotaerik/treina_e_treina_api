package com.yokota.treino.model.workout;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String description;

    private Boolean isTemplate;

    @OneToMany
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    private User user;

    private LocalDate createdAt;

    public Workout(Long id, String name, String description, Boolean isTemplate, List<Exercise> exercises, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isTemplate = isTemplate;
        this.exercises = exercises;
        this.createdAt = createdAt;
    }
}

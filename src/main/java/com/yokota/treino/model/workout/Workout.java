package com.yokota.treino.model.workout;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.worksheet.Worksheet;
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


    @OneToMany(cascade=CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    private Worksheet template;

    @ManyToOne
    private User user;

    private LocalDate createdAt;

}

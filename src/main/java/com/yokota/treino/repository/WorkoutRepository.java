package com.yokota.treino.repository;


import com.yokota.treino.model.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}

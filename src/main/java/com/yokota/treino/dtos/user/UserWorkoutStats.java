package com.yokota.treino.dtos.user;

import com.yokota.treino.dtos.set.SetResponseDTO;
import com.yokota.treino.dtos.workout.WorkoutIdDTO;

import java.util.List;

public record UserWorkoutStats(List<WorkoutIdDTO> workouts, List<SetResponseDTO> heaviestSets, List<SetResponseDTO> bestSets) {
}

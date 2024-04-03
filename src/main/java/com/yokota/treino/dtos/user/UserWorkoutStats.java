package com.yokota.treino.dtos.user;

import com.yokota.treino.dtos.set.SetResponseDTO;
import com.yokota.treino.dtos.workout.WorkoutIdDTO;
import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;

import java.util.List;

public record UserWorkoutStats(WorksheetResponseDTO worksheet, List<WorkoutResponseDTO> workouts, List<SetResponseDTO> heaviestSets, List<SetResponseDTO> bestSets) {
}

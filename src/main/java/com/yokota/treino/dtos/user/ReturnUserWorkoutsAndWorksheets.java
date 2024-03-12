package com.yokota.treino.dtos.user;

import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;

import java.util.List;

public record ReturnUserWorkoutsAndWorksheets(List<WorksheetResponseDTO> worksheets, List<WorkoutResponseDTO> workouts) {
}

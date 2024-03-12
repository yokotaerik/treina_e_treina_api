package com.yokota.treino.dtos.workout;

import com.yokota.treino.dtos.exercise.ExerciseResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record WorkoutResponseDTO(Long id, WorksheetResponseDTO worksheet, List<ExerciseResponseDTO> exercises, LocalDate createdAt) {
}

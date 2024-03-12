package com.yokota.treino.dtos.worksheet;

import com.yokota.treino.dtos.exercise.ExerciseInWorksheetResponseDTO;

import java.util.List;

public record WorksheetResponseDTO(Long id, String name, String description, List<ExerciseInWorksheetResponseDTO> exercises) {
}

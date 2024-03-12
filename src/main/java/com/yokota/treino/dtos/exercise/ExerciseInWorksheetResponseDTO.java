package com.yokota.treino.dtos.exercise;

public record ExerciseInWorksheetResponseDTO(Long id, ExerciseInfoResponseDTO info, int numberOfSets, String notes) {
}

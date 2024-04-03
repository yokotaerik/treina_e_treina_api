package com.yokota.treino.dtos.set;

import com.yokota.treino.dtos.exercise.ExerciseInfoResponseDTO;

public record SetResponseDTO(Long id, double minutesResting, double reps, double weight, String notes, String exercise) {
}

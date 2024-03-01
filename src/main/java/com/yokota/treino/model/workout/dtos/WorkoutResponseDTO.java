package com.yokota.treino.model.workout.dtos;

import com.yokota.treino.model.exercise.dtos.ExerciseResponseDTO;

import java.util.List;

public record WorkoutResponseDTO(Long id, String name, String description, List<ExerciseResponseDTO> exercises, Boolean isTemplate) {
}

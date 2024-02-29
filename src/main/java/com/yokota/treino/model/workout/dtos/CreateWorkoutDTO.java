package com.yokota.treino.model.workout.dtos;

import com.yokota.treino.model.exercise.dtos.AddExerciseDTO;

import java.util.List;

public record CreateWorkoutDTO(String name, String description, List<AddExerciseDTO> exercisesDTOS){
}

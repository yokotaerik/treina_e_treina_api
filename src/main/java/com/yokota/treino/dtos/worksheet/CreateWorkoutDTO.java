package com.yokota.treino.dtos.worksheet;


import com.yokota.treino.dtos.exercise.AddExerciseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateWorkoutDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        String name,
        @NotBlank(message = "A descrição não pode estar em branco")
        String description,
        @NotNull(message = "A lista de exercícios não pode ser nula")
        @Size(min = 1, message = "A lista de exercícios deve conter pelo menos um exercício")
        List<@Valid AddExerciseDTO> exercisesDTOS
) {}
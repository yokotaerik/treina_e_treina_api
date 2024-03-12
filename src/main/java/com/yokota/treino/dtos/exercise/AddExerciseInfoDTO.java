package com.yokota.treino.dtos.exercise;

import jakarta.validation.constraints.NotBlank;

public record AddExerciseInfoDTO(
        @NotBlank(message = "Name is required") String name
) {}

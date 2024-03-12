package com.yokota.treino.dtos.exercise;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddExerciseDTO(
        @NotNull(message = "Exercise ID cannot be null") Long exerciseId,
        @Min(value = 1, message = "Number of sets must be at least 1") int numberOfSets,
        @Nullable() String notes

) {}
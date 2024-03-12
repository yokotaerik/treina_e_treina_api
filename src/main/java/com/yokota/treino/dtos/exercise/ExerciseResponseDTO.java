package com.yokota.treino.dtos.exercise;

import com.yokota.treino.dtos.set.SetResponseDTO;

import java.util.List;

public record ExerciseResponseDTO(Long id, ExerciseInfoResponseDTO info, List<SetResponseDTO> sets) {
}

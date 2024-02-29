package com.yokota.treino.model.exercise.dtos;

import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.set.SetResponseDTO;

import java.util.List;

public record ExerciseResponseDTO(Long id, ExerciseInfoResponseDTO info, List<SetResponseDTO> sets) {
}

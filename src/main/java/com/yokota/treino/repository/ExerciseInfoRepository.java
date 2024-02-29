package com.yokota.treino.repository;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseInfoRepository extends JpaRepository<ExerciseInfo, Long> {
}

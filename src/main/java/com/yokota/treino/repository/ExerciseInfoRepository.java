package com.yokota.treino.repository;

import com.yokota.treino.model.exercise.ExerciseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseInfoRepository extends JpaRepository<ExerciseInfo, Long> {

    Optional<ExerciseInfo> findByName(String name);
}

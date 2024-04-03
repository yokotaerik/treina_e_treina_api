package com.yokota.treino.repository;

import com.yokota.treino.model.set.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {

    @Query(value = "SELECT s " +
            "FROM Set s " +
            "JOIN ( " +
            "    SELECT ei.id AS exercise_id, " +
            "           MAX(CASE WHEN s2.weight > 0 THEN s2.weight ELSE 0 END) AS max_weight " +
            "    FROM Set s2 " +
            "    JOIN Workout w ON s2.workout.id = w.id " +
            "    JOIN Worksheet ws ON w.template.id = ws.id " +
            "    JOIN ExerciseInfo ei ON s2.exercise.id = ei.id " +
            "    WHERE ws.id = :worksheetId AND w.user.id = :userId " +
            "    GROUP BY ei.id " +
            ") max_weights ON s.exercise.id = max_weights.exercise_id AND s.weight = max_weights.max_weight " +
            "WHERE max_weights.max_weight > 0")
    List<Set> findMaxWeightSetsByWorksheetAndUserId(@Param("worksheetId") Long worksheetId, @Param("userId") Long userId);


    @Query(value = "SELECT s.*, " +
            "CASE " +
            "   WHEN (s.weight * s.reps) > 0 THEN s.weight * s.reps " +
            "   ELSE s.reps " +
            "END AS max_load " +
            "FROM set_table s " +
            "JOIN ( " +
            "    SELECT ei.id AS exercise_id, " +
            "    CASE " +
            "       WHEN MAX(CASE WHEN (s2.weight * s2.reps) > 0 THEN s2.weight * s2.reps ELSE s2.reps END) = 0 THEN MAX(s2.reps) " +
            "       ELSE MAX(CASE WHEN (s2.weight * s2.reps) > 0 THEN s2.weight * s2.reps ELSE s2.reps END) " +
            "    END AS max_load " +
            "    FROM set_table s2 " +
            "    JOIN workout w ON s2.workout_id = w.id " +
            "    JOIN worksheet ws ON w.template_id = ws.id " +
            "    JOIN exercise_info ei ON s2.exercise_id = ei.id " +
            "    WHERE ws.id = :worksheetId AND w.user_id = :userId " +
            "    GROUP BY ei.id " +
            ") max_loads ON s.exercise_id = max_loads.exercise_id AND (CASE WHEN (s.weight * s.reps) > 0 THEN s.weight * s.reps ELSE s.reps END) = max_loads.max_load",
            nativeQuery = true)
    List<Set> findMaxLoadSetsByWorksheetAndUserId(@Param("worksheetId") Long worksheetId, @Param("userId") Long userId);

}

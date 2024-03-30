package com.yokota.treino.repository;

import com.yokota.treino.model.set.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {

    @Query(value = "SELECT s.* " + // Ajuste esta linha conforme a estrutura da sua tabela e entidade
            "FROM set_table s " +
            "JOIN ( " +
            "    SELECT ei.id AS exercise_id, MAX(s2.weight) AS max_weight " +
            "    FROM set_table s2 " +
            "    JOIN workout w ON s2.workout_id = w.id " +
            "    JOIN worksheet ws ON w.template_id = ws.id " +
            "    JOIN exercise_info ei ON s2.exercise_id = ei.id " +
            "    WHERE ws.id = :worksheetId AND w.user_id = :userId " +
            "    GROUP BY ei.id " +
            ") max_weights ON s.exercise_id = max_weights.exercise_id AND s.weight = max_weights.max_weight",
            nativeQuery = true)
    List<Set> findMaxWeightSetsByWorksheetAndUserId(@Param("worksheetId") Long worksheetId, @Param("userId") Long userId);
}

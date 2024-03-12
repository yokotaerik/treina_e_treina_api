package com.yokota.treino.repository;

import com.yokota.treino.model.set.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Long> {
}

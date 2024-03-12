package com.yokota.treino.repository;


import com.yokota.treino.model.worksheet.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {
}

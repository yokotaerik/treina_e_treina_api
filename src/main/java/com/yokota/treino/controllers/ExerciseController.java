package com.yokota.treino.controllers;

import com.yokota.treino.dtos.exercise.AddExerciseInfoDTO;
import com.yokota.treino.expcetions.ExceptionResponse;
import com.yokota.treino.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping("/add")
    public ResponseEntity<?> addExerciseInfo(@RequestBody @Valid AddExerciseInfoDTO data) {
        exerciseService.createExerciseInfo(data);
        return ResponseEntity.status(201).body("Exercício adicionado");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExercises() {
        var exercises = exerciseService.returnAllExerciseInfos();
        return ResponseEntity.ok(exercises);
    }
}

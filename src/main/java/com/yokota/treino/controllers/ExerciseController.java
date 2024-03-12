package com.yokota.treino.controllers;

import com.yokota.treino.dtos.exercise.AddExerciseInfoDTO;
import com.yokota.treino.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping("/add")
    public ResponseEntity<String> addExerciseInfo(@RequestBody @Valid AddExerciseInfoDTO data) {
        if (exerciseService.exerciseExists(data.name())) {
            return ResponseEntity.badRequest().body("Exercício já cadastrado");
        }

        exerciseService.createExerciseInfo(data);
        return ResponseEntity.ok("Exercício adicionado");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExercises() {
        var exercises = exerciseService.returnAllExerciseInfos();
        return ResponseEntity.ok(exercises);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

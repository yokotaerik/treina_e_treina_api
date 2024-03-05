package com.yokota.treino.controllers;

import com.yokota.treino.model.exercise.dtos.AddExerciseInfoDTO;
import com.yokota.treino.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;


    @PostMapping("/add")
    public ResponseEntity<?> addExerciseInfo(@RequestBody AddExerciseInfoDTO data){
            exerciseService.createExerciseInfo(data);

            return ResponseEntity.ok("exercise added");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExercises(){

        var exercises = exerciseService.returnAllExerciseInfos();

        return ResponseEntity.ok(exercises);
    }
}

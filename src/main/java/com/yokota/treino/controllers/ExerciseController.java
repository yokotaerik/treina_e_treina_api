package com.yokota.treino.controllers;

import com.yokota.treino.model.exercise.dtos.AddExerciseInfoDTO;
import com.yokota.treino.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;


    @PostMapping("/add/newExercise")
    public ResponseEntity<?> addExerciseInfo(@RequestBody AddExerciseInfoDTO data){
            exerciseService.createExerciseInfo(data);

            return ResponseEntity.ok("exercise added");
    }
}

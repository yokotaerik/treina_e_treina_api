package com.yokota.treino.controllers;

import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.UserService;
import com.yokota.treino.service.WorkoutService;
import com.yokota.treino.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    WorksheetService worksheetService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(@PathVariable Long id){

        Workout workout = workoutService.findById(id);

        WorkoutResponseDTO response = workoutService.returnWorkout(workout);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/start/{id}")
    public ResponseEntity<?> startWorkout(@PathVariable Long id) {
        User user = authorizationService.getCurrentUser();

        Worksheet worksheet = worksheetService.findById(id);

        workoutService.startNewWorkout(worksheet, user);

        return ResponseEntity.status(201).body("Workout started");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
        User user = authorizationService.getCurrentUser();

        Workout workout = workoutService.findById(id);

        workoutService.deleteWorkout(workout, user);

        return ResponseEntity.ok("DELETED");
    }
}

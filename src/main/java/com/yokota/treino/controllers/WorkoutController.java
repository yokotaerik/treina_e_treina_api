package com.yokota.treino.controllers;

import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.CreateWorkoutDTO;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/add")
    public ResponseEntity<?> addExerciseInfo(@RequestBody CreateWorkoutDTO data) throws Exception {
            User user = authorizationService.getCurrentUser();

            workoutService.createNewWorkout(data, user);

            return ResponseEntity.ok("exercise added");
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserWorkout(@RequestBody CreateWorkoutDTO data) throws Exception {
        User user = authorizationService.getCurrentUser();

        var response = workoutService.returnUserWorkouts(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/start/{id}")
    public ResponseEntity<?> startWorkout(@PathVariable Long id) throws Exception {
        User user = authorizationService.getCurrentUser();

        Workout workout = workoutService.findById(id);

        workoutService.startNewWorkout(workout);

        return ResponseEntity.ok("Workout started");
    }
}

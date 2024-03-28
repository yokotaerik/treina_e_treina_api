package com.yokota.treino.controllers;

import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.UserService;
import com.yokota.treino.service.WorkoutService;
import com.yokota.treino.service.WorksheetService;
import org.hibernate.jdbc.Work;
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
    public ResponseEntity<?> getWorkoutById(@PathVariable Long id) throws Exception {
        User user = authorizationService.getCurrentUser();

        Workout workout = workoutService.findById(id);

        var response = workoutService.returnWorkout(workout);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/start/{id}")
    public ResponseEntity<?> startWorkout(@PathVariable Long id) throws Exception {
        User user = authorizationService.getCurrentUser();

        Worksheet worksheet = worksheetService.findById(id);

        workoutService.startNewWorkout(worksheet, user);

        return ResponseEntity.ok("Workout started");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) throws Exception {
        User user = authorizationService.getCurrentUser();

        Workout workout = workoutService.findById(id);

        workoutService.deleteWorkout(workout);

        return ResponseEntity.ok("DELETED");
    }
}

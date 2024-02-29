package com.yokota.treino.controllers;

import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.dtos.CreateWorkoutDTO;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    WorkoutMapper workoutMapper;


    @PostMapping("/add/newWorkout")
    public ResponseEntity<?> addExerciseInfo(@RequestBody CreateWorkoutDTO data) throws Exception {
            User user = authorizationService.getCurrentUser();

            workoutService.createNewWorkout(data, user);

            return ResponseEntity.ok("exercise added");
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getUserWorkout(@RequestBody CreateWorkoutDTO data) throws Exception {
        User user = authorizationService.getCurrentUser();

        var workouts = user.getWorkouts();

        List<WorkoutResponseDTO> response = workoutMapper.workoutResponseDTOList(workouts);

        return ResponseEntity.ok(response);
    }
}

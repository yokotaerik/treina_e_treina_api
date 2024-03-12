package com.yokota.treino.controllers;

import com.yokota.treino.dtos.user.ReturnUserWorkoutsAndWorksheets;
import com.yokota.treino.model.user.User;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.WorkoutService;
import com.yokota.treino.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    WorksheetService worksheetService;

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/workouts")
    public ResponseEntity<?> getUserWorkout() throws Exception {
        User user = authorizationService.getCurrentUser();

        var worksheets =  worksheetService.returnUserWorksheetList(user);

        var workouts = workoutService.returnUserWorkouts(user);

        return ResponseEntity.ok(new ReturnUserWorkoutsAndWorksheets(worksheets, workouts));
    }


    @GetMapping("/archived_worksheets")
    public ResponseEntity<?> getArchivedWorksheets() throws Exception {
        User user = authorizationService.getCurrentUser();

        var worksheets =  worksheetService.returnUserArchivedWorksheetList(user);

        return ResponseEntity.ok(worksheets);
    }
}

package com.yokota.treino.controllers;

import com.yokota.treino.dtos.user.ReturnUserWorkoutsAndWorksheets;
import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;
import com.yokota.treino.model.user.User;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.WorkoutService;
import com.yokota.treino.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<?> getUserWorkouts(){
        User user = authorizationService.getCurrentUser();

        List<WorksheetResponseDTO> worksheets =  worksheetService.returnUserWorksheetList(user);

        List<WorkoutResponseDTO> workouts = workoutService.returnUserWorkouts(user);

        return ResponseEntity.ok(new ReturnUserWorkoutsAndWorksheets(worksheets, workouts));
    }


    @GetMapping("/archived_worksheets")
    public ResponseEntity<?> getArchivedWorksheets() {
        User user = authorizationService.getCurrentUser();

        List<WorksheetResponseDTO> worksheets =  worksheetService.returnUserArchivedWorksheetList(user);

        return ResponseEntity.ok(worksheets);
    }
}

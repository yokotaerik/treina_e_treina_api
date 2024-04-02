package com.yokota.treino.controllers;

import com.yokota.treino.dtos.set.SetResponseDTO;
import com.yokota.treino.dtos.user.ReturnUserWorkoutsAndWorksheets;
import com.yokota.treino.dtos.user.UserWorkoutStats;
import com.yokota.treino.dtos.workout.WorkoutIdDTO;
import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;
import com.yokota.treino.mappers.SetMapper;
import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.SetService;
import com.yokota.treino.service.WorkoutService;
import com.yokota.treino.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    SetService setService;

    @Autowired
    SetMapper setMapper;

    @Autowired
    WorkoutMapper workoutMapper;

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


    @GetMapping("/worksheet/{id}")
    public ResponseEntity<?> getWorkoutsFilterByWorksheetId(@PathVariable Long id){

        User user = authorizationService.getCurrentUser();
        Worksheet worksheet = worksheetService.findById(id);

        List<Workout> workoutList = workoutService.getUsersWorkoutsByWorksheet(worksheet, user);

        List<Set> heaviestSets = setService.getHeaviestSets(worksheet, user);

        List<Set> bestSets = setService.getBestSets(worksheet, user);

        List<SetResponseDTO> heaviestSetsDTO = setMapper.setListToSetListResponseDTO(heaviestSets);

        List<SetResponseDTO> bestSetsDTO = setMapper.setListToSetListResponseDTO(bestSets);

        List<WorkoutIdDTO> workouts = workoutMapper.workoutIdDTOList(workoutList);

        return ResponseEntity.ok(new UserWorkoutStats(workouts, heaviestSetsDTO, bestSetsDTO));
    }
}

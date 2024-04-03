package com.yokota.treino.controllers;

import com.yokota.treino.dtos.set.SetResponseDTO;
import com.yokota.treino.dtos.user.ReturnUserWorkoutsAndWorksheets;
import com.yokota.treino.dtos.user.UserWorkoutStats;
import com.yokota.treino.dtos.workout.WorkoutIdDTO;
import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;
import com.yokota.treino.mappers.SetMapper;
import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.mappers.WorksheetMapper;
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

    @Autowired
    WorksheetMapper worksheetMapper;

    @GetMapping("/workouts")
    public ResponseEntity<?> getUserWorkouts(){
        User user = authorizationService.getCurrentUser();

        List<Worksheet> worksheets =  worksheetService.returnUserWorksheetList(user);

        List<WorksheetResponseDTO> worksheetResponseDTOList = worksheetMapper.worksheetResponseToDTOList(worksheets);

        List<Workout> workouts = workoutService.returnUserWorkouts(user);

        List<WorkoutResponseDTO> workoutResponseDTOList = workoutMapper.workoutResponseDTOList(workouts);


        return ResponseEntity.ok(new ReturnUserWorkoutsAndWorksheets(worksheetResponseDTOList, workoutResponseDTOList));
    }


    @GetMapping("/archived_worksheets")
    public ResponseEntity<?> getArchivedWorksheets() {
        User user = authorizationService.getCurrentUser();

        List<Worksheet> worksheets =  worksheetService.returnUserArchivedWorksheetList(user);

        List<WorksheetResponseDTO>response = worksheetMapper.worksheetResponseToDTOList(worksheets);


        return ResponseEntity.ok(response);
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

        WorksheetResponseDTO worksheetResponseDTO = worksheetMapper.worksheetToDTO(worksheet);

        List<WorkoutResponseDTO> workouts = workoutMapper.workoutResponseDTOList(workoutList);

        return ResponseEntity.ok(new UserWorkoutStats(worksheetResponseDTO, workouts, heaviestSetsDTO, bestSetsDTO));
    }
}

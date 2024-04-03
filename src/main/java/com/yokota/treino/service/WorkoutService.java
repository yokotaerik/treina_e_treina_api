package com.yokota.treino.service;

import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInWorksheet;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.dtos.workout.WorkoutResponseDTO;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class WorkoutService {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorksheetRepository worksheetRepository;

    @Autowired
    SetRepository setRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    LocalDate now = LocalDate.now();
    // Encontra um treino pelo ID
    public Workout findById(Long id){
        return workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout with ID " + id + " not found."));
    }

    //Delete o treino
    public void deleteWorkout(Workout workout, User user){
        if(!workout.getUser().equals(user)){
            throw new AccessDeniedException("You must be owner of this workout");
        }
        workoutRepository.delete(workout);
    }

    // Converte um treino em um DTO de resposta
    public WorkoutResponseDTO returnWorkout(Workout workout){
        return workoutMapper.workoutToDTO(workout);
    }

    public List<WorkoutResponseDTO> returnWorkoutList(List<Workout> workouts){
        return workoutMapper.workoutResponseDTOList(workouts);
    }



    // Retorna todos os treinos do usuário
    public List<Workout> returnUserWorkouts(User user){
        List<Workout> workouts = user.getWorkouts();

        workouts.sort(new Comparator<Workout>() {
            @Override
            public int compare(Workout w1, Workout w2) {
                return w2.getCreatedAt().compareTo(w1.getCreatedAt());
            }
        });

        return workouts;
    }

    // Inicia um novo treino com base em uma planilha de treino
    public Workout startNewWorkout(Worksheet worksheet, User user){
        Workout newWorkout = new Workout();
        newWorkout.setUser(user);
        newWorkout.setCreatedAt(now);
        newWorkout.setTemplate(worksheet);
        workoutRepository.save(newWorkout);

        List<Exercise> newExercises = new ArrayList<>();
        for (ExerciseInWorksheet exercise : worksheet.getExerciseInWorksheet()) {
            var newExercise = exerciseService.createExerciseEntity(exercise.getInfo(), exercise.getNumberOfSets(), newWorkout);
            newExercises.add(newExercise);
        }

        newWorkout.setExercises(newExercises);
        workoutRepository.save(newWorkout);
        return newWorkout;
    }

    public List<Workout> getUsersWorkoutsByWorksheet(Worksheet worksheet, User user){
        return user.getWorkouts().stream().filter((workout -> workout.getTemplate().equals(worksheet))).toList();
    }

}

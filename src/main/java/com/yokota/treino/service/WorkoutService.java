package com.yokota.treino.service;

import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.CreateWorkoutDTO;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import com.yokota.treino.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    SetRepository setRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    ModelMapper modelMapper = new ModelMapper();

    LocalDate now = LocalDate.now();

    public void createNewWorkout(CreateWorkoutDTO data){


        var template = new Workout(null, data.name(), data.description(), true, null, now);
        workoutRepository.save(template);
        var exercises = exerciseService.createExerciseList(data.exercisesDTOS(), template);
        template.setExercises(exercises);
        workoutRepository.save(template);
    }

    public Workout findById(Long id){
        return workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout with ID " + id + " not found."));
    }

    public WorkoutResponseDTO returnWorkout(Workout workout){
        return workoutMapper.workoutToDTO(workout);
    }

    public List<WorkoutResponseDTO> returnUserWorkouts(User user){

        List<Workout> workouts = user.getWorkouts();

        return workoutMapper.workoutResponseDTOList(workouts);
    }

    public void startNewWorkout(Workout template, User user){

        // Criando uma nova instância de Workout
        Workout newWorkout = new Workout();
        newWorkout.setName(template.getName());
        newWorkout.setUser(user);
        newWorkout.setIsTemplate(false);
        newWorkout.setCreatedAt(now);


        workoutRepository.save(newWorkout);

        // Criando uma nova lista de exercícios para o novo treino
        List<Exercise> newExercises = new ArrayList<>();
        for (Exercise exercise : template.getExercises()) {
            var newExercise = exerciseService.createExerciseEntity(exercise.getInfo(), exercise.getSets().size(), newWorkout);
            newExercises.add(newExercise);
        }


        newWorkout.setExercises(newExercises);

        newWorkout = workoutRepository.save(newWorkout);
    }

}

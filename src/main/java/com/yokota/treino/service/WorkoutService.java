package com.yokota.treino.service;

import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.CreateWorkoutDTO;
import com.yokota.treino.repository.UserRepository;
import com.yokota.treino.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    public void createNewWorkout(CreateWorkoutDTO data, User user){

        var exercises = exerciseService.createExerciseList(data.exerciseDTOS());

        var workout = new Workout(null, data.name(), data.description(), exercises, user);
        user.getWorkouts().add(workout);
        workoutRepository.save(workout);
        userRepository.save(user);

    }

}

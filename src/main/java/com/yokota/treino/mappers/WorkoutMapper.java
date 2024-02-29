package com.yokota.treino.mappers;

import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutMapper {

    @Autowired
    ExerciseMapper exerciseMapper;

    public WorkoutResponseDTO workoutToDTO(Workout workout){
        return new WorkoutResponseDTO(workout.getId(), workout.getName(), workout.getDescription(), exerciseMapper.exerciseResponseDTOList(workout.getExercises()));
    }

    public List<WorkoutResponseDTO> workoutResponseDTOList(List<Workout> workouts){
        return workouts.stream().map(this::workoutToDTO).collect(Collectors.toList());
    }
}

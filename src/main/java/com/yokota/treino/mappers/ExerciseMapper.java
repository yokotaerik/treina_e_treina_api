package com.yokota.treino.mappers;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.model.exercise.dtos.ExerciseInfoResponseDTO;
import com.yokota.treino.model.exercise.dtos.ExerciseResponseDTO;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ExerciseMapper {

    @Autowired
    SetMapper setMapper;

    public ExerciseResponseDTO exerciseToDTO(Exercise exercise){
        return new ExerciseResponseDTO(exercise.getId(), exerciseInfoToDTO(exercise.getInfo()), setMapper.setListToSetListResponseDTO(exercise.getSets()));
    }

    public List<ExerciseResponseDTO> exerciseResponseDTOList(List<Exercise> exercises){
        return exercises.stream().map(this::exerciseToDTO).collect(Collectors.toList());
    }


    public ExerciseInfoResponseDTO exerciseInfoToDTO(ExerciseInfo exerciseInfo){
        return new ExerciseInfoResponseDTO(exerciseInfo.getId(), exerciseInfo.getName(), exerciseInfo.getDescription());
    }
}

package com.yokota.treino.mappers;

import com.yokota.treino.dtos.exercise.ExerciseInWorksheetResponseDTO;
import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.dtos.exercise.ExerciseInfoResponseDTO;
import com.yokota.treino.dtos.exercise.ExerciseResponseDTO;
import com.yokota.treino.model.exercise.ExerciseInWorksheet;
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
        return new ExerciseInfoResponseDTO(exerciseInfo.getId(), exerciseInfo.getName());
    }

    public List<ExerciseInfoResponseDTO> exerciseInfoResponseDTOS(List<ExerciseInfo> infos){
        return infos.stream().map(this::exerciseInfoToDTO).collect(Collectors.toList());
    }

    public ExerciseInWorksheetResponseDTO exerciseSetsToDTO(ExerciseInWorksheet exercise){
        return new ExerciseInWorksheetResponseDTO(exercise.getId(), exerciseInfoToDTO(exercise.getInfo()), exercise.getNumberOfSets(), exercise.getNote());
    }

    public List<ExerciseInWorksheetResponseDTO> exerciseSetsResponseDTOList(List<ExerciseInWorksheet> exercises){
        return exercises.stream().map(this::exerciseSetsToDTO).collect(Collectors.toList());
    }

}

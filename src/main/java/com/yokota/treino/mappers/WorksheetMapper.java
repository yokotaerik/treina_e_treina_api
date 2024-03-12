package com.yokota.treino.mappers;

import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;
import com.yokota.treino.model.worksheet.Worksheet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorksheetMapper {

    @Autowired
    private ExerciseMapper exerciseMapper;

    ModelMapper modelMapper = new ModelMapper();

    public WorksheetResponseDTO worksheetToDTO(Worksheet worksheet){
        return new WorksheetResponseDTO(worksheet.getId(), worksheet.getName(), worksheet.getDescription(), exerciseMapper.exerciseSetsResponseDTOList(worksheet.getExerciseInWorksheet()));
    }

    public List<WorksheetResponseDTO> worksheetResponseToDTOList(List<Worksheet> worksheetList){
        return worksheetList.stream().map(this::worksheetToDTO).collect(Collectors.toList());
    }
}

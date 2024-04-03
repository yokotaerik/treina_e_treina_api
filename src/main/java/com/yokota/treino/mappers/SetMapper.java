package com.yokota.treino.mappers;

import com.yokota.treino.model.set.Set;
import com.yokota.treino.dtos.set.SetResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SetMapper {

    public SetResponseDTO setToSetResponseDTO(Set set){
        return new SetResponseDTO(set.getId(), set.getMinutesResting(), set.getReps(), set.getWeight(), set.getNotes(), set.getExercise().getName());
    }

    public List<SetResponseDTO> setListToSetListResponseDTO(List<Set> sets){
        return sets.stream().map((this::setToSetResponseDTO)).collect(Collectors.toList());
    }
}

package com.yokota.treino.service;

import com.yokota.treino.mappers.ExerciseMapper;
import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.dtos.exercise.AddExerciseDTO;
import com.yokota.treino.dtos.exercise.AddExerciseInfoDTO;
import com.yokota.treino.dtos.exercise.ExerciseInfoResponseDTO;
import com.yokota.treino.model.exercise.ExerciseInWorksheet;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.repository.ExerciseInfoRepository;
import com.yokota.treino.repository.ExerciseRepository;
import com.yokota.treino.repository.ExerciseInWorksheetRepository;
import com.yokota.treino.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseInfoRepository exerciseInfoRepository;

    @Autowired
    ExerciseInWorksheetRepository exerciseInWorksheetRepository;

    @Autowired
    SetRepository setRepository;

    @Autowired
    ExerciseMapper exerciseMapper;

    // Método para criar informações de exercício
    public void createExerciseInfo(AddExerciseInfoDTO data) {

        if (exerciseExists(data.name())){
            throw new IllegalArgumentException("Esse exercicio ja esta cadastrado!");
        }

        ExerciseInfo exerciseInfo = new ExerciseInfo(null, data.name());
        exerciseInfoRepository.save(exerciseInfo);
    }

    // Método para criar entidade de exercício em uma planilha de treino
    public ExerciseInWorksheet createExerciseInWorksheetEntity(ExerciseInfo exerciseInfo, int numberOfSets, String note) {

        var exercise = new ExerciseInWorksheet(null, exerciseInfo, numberOfSets, note);

        exerciseInWorksheetRepository.save(exercise);
        return exercise;
    }

    // Método para criar uma lista de entidades de exercício em uma planilha de treino
    public List<ExerciseInWorksheet> createExerciseInWorksheetList (List<AddExerciseDTO> data){

        List<ExerciseInWorksheet> exercises = new ArrayList<>();

        data.forEach(addExerciseDTO -> {
            try {
                ExerciseInfo exerciseInfo = findExerciseInfoById(addExerciseDTO.exerciseId());
                exercises.add(createExerciseInWorksheetEntity(exerciseInfo, addExerciseDTO.numberOfSets(), addExerciseDTO.notes()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return exercises;
    }

    // Método para criar entidade de exercício em um treino
    public Exercise createExerciseEntity(ExerciseInfo exerciseInfo, int numberOfSets, Workout workout) {

        Exercise exercise = new Exercise(null, exerciseInfo, null);

        List<Set> sets = new ArrayList<>();
        for (int i = 0; i < numberOfSets; i++) {
            var set = new Set();
            set.setWorkout(workout);
            sets.add(set);
            setRepository.save(set);
        }

        exercise.setSets(sets);

        exerciseRepository.save(exercise);

        return exercise;
    }

    // Método para encontrar informações de exercício por ID
    public ExerciseInfo findExerciseInfoById(Long id) {
        return exerciseInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise with ID " + id + " not found."));
    }

    // Método para criar uma lista de entidades de exercício em um treino
    public List<Exercise> createExerciseList(List<AddExerciseDTO> data, Workout workout){
        List<Exercise> exercises = new ArrayList<>();

        data.forEach(addExerciseDTO -> {
            try {
                ExerciseInfo exerciseInfo = findExerciseInfoById(addExerciseDTO.exerciseId());
                exercises.add(createExerciseEntity(exerciseInfo, addExerciseDTO.numberOfSets(), workout));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return exercises;
    }

    // Método para verificar se um exercício existe pelo nome
    public boolean exerciseExists(String name){
        var optional = exerciseInfoRepository.findByName(name);
        return optional.isPresent();
    }

    // Método para retornar todas as informações de exercício
    public List<ExerciseInfoResponseDTO> returnAllExerciseInfos(){
        var infos = exerciseInfoRepository.findAll();
        return exerciseMapper.exerciseInfoResponseDTOS(infos);
    }
}

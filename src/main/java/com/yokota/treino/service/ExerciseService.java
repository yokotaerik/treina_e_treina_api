package com.yokota.treino.service;

import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.model.exercise.dtos.AddExerciseDTO;
import com.yokota.treino.model.exercise.dtos.AddExerciseInfoDTO;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.repository.ExerciseInfoRepository;
import com.yokota.treino.repository.ExerciseRepository;
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
    SetRepository setRepository;

    public void createExerciseInfo(AddExerciseInfoDTO data) {
        ExerciseInfo exerciseInfo = new ExerciseInfo(null, data.name(), data.description());
        exerciseInfoRepository.save(exerciseInfo);
    }

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

    public ExerciseInfo findExerciseInfoById(Long id) {
//        Retorna a entidade pelo id, caso não achar lança uma exceção
        return exerciseInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise with ID " + id + " not found."));
    }


    public List<Exercise> createExerciseList(List<AddExerciseDTO> data, Workout workout){
        // Recebe uma lista de DTOs com informacoes para adicionar o exercicio e retorna as respectivas entidades
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
}

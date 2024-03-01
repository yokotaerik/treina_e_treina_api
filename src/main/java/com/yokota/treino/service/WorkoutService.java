package com.yokota.treino.service;

import com.yokota.treino.mappers.WorkoutMapper;
import com.yokota.treino.model.exercise.Exercise;
import com.yokota.treino.model.exercise.ExerciseInfo;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.workout.Workout;
import com.yokota.treino.model.workout.dtos.CreateWorkoutDTO;
import com.yokota.treino.model.workout.dtos.WorkoutResponseDTO;
import com.yokota.treino.repository.ExerciseRepository;
import com.yokota.treino.repository.SetRepository;
import com.yokota.treino.repository.UserRepository;
import com.yokota.treino.repository.WorkoutRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void createNewWorkout(CreateWorkoutDTO data, User user){

        var exercises = exerciseService.createExerciseList(data.exercisesDTOS());

        var workout = new Workout(null, data.name(), data.description(), true, exercises, user);
        user.getWorkouts().add(workout);
        workoutRepository.save(workout);
        userRepository.save(user);
    }

    public Workout findById(Long id){
        return workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout with ID " + id + " not found."));
    }

    public WorkoutResponseDTO returnWorkout(Workout workout){
        return workoutMapper.workoutToDTO(workout);
    }

    public List<WorkoutResponseDTO> returnUserWorkouts(User user){

        var workouts = user.getWorkouts();

        return workoutMapper.workoutResponseDTOList(workouts);
    }

    public void startNewWorkout(Workout originalWorkout) {
        // Obtendo o usuário associado ao treino original
        User user = originalWorkout.getUser();

        // Criando uma cópia do treino original
        Workout newWorkout = createCopyOfWorkout(originalWorkout, user);

        // Salvando o novo treino e suas instâncias associadas
        newWorkout = saveNewWorkout(newWorkout);

        // Atualizando as referências do novo treino nas instâncias de exercícios e conjuntos
        updateReferencesInExercisesAndSets(newWorkout);
    }

    private Workout createCopyOfWorkout(Workout originalWorkout, User user) {
        Workout newWorkout = new Workout();
        newWorkout.setName(originalWorkout.getName());
        newWorkout.setDescription(originalWorkout.getDescription());
        newWorkout.setUser(user);
        newWorkout.setIsTemplate(false);

        // Copiando os exercícios do treino original para o novo treino
        List<Exercise> newExercises = copyExercises(originalWorkout.getExercises());
        newWorkout.setExercises(newExercises);

        return newWorkout;
    }

    private List<Exercise> copyExercises(List<Exercise> originalExercises) {
        List<Exercise> newExercises = new ArrayList<>();

        for (Exercise originalExercise : originalExercises) {
            Exercise newExercise = new Exercise();
            // Copiando as informações do ExerciseInfo
            newExercise.setInfo(originalExercise.getInfo());
            // Copiando os conjuntos (sets)
            List<Set> newSets = copySets(originalExercise.getSets(), newExercise);
            // Definindo os novos conjuntos no novo exercício
            newExercise.setSets(newSets);
            newExercises.add(newExercise);
        }

        return newExercises;
    }

    private List<Set> copySets(List<Set> originalSets, Exercise newExercise) {
        List<Set> newSets = new ArrayList<>();

        for (Set originalSet : originalSets) {
            Set newSet = new Set();
            newSets.add(newSet);
        }

        return newSets;
    }

    private Workout saveNewWorkout(Workout newWorkout) {
        return workoutRepository.save(newWorkout);
    }

    private void updateReferencesInExercisesAndSets(Workout newWorkout) {
        for (Exercise exercise : newWorkout.getExercises()) {
            exerciseRepository.save(exercise);
            for (Set set : exercise.getSets()) {
                set.setWorkout(newWorkout);
                setRepository.save(set);
            }
        }
    }


}

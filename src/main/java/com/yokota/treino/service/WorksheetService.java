package com.yokota.treino.service;

import com.yokota.treino.dtos.worksheet.CreateWorkoutDTO;
import com.yokota.treino.dtos.worksheet.WorksheetResponseDTO;
import com.yokota.treino.mappers.WorksheetMapper;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.repository.UserRepository;
import com.yokota.treino.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorksheetService {

    @Autowired
    WorksheetRepository worksheetRepository;

    @Autowired
    WorksheetMapper worksheetMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseService exerciseService;


    LocalDate now = LocalDate.now();

    // Cria uma nova planilha de treino
    public void createNewWorksheet(CreateWorkoutDTO data, User user){
        var template = new Worksheet(null, data.name(), data.description(), null, now);
        worksheetRepository.save(template);
        var exercises = exerciseService.createExerciseInWorksheetList(data.exercisesDTOS());
        template.setExerciseInWorksheet(exercises);
        worksheetRepository.save(template);
        user.getWorksheets().add(template);
        userRepository.save(user);
    }

    // Retorna um DTO de Worksheet
    public WorksheetResponseDTO returnWorksheet(Worksheet worksheet){
        return worksheetMapper.worksheetToDTO(worksheet);
    }

    // Encontra uma planilha de treino pelo ID
    public Worksheet findById(Long id) {
        return worksheetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Worksheet with ID " + id + " not found."));
    }

    // Retorna uma lista de DTOs de resposta de planilha de treino do usuário
    public List<Worksheet> returnUserWorksheetList(User user) {
        return user.getWorksheets();
    }

    public List<Worksheet> returnUserArchivedWorksheetList(User user) {
        return user.getArchivedWorksheets();
    }

    // Deleta a worksheet e sua dependencias
    public void deleteWorksheet(Worksheet worksheet, User user){

        user.getWorksheets().remove(worksheet);

        worksheetRepository.delete(worksheet);

        userRepository.save(user);
    }

    // Arquiva a worksheet de um usuario
    public void archiveWorksheet(Worksheet worksheet, User user){

        user.getWorksheets().remove(worksheet);

        user.getArchivedWorksheets().add(worksheet);

        userRepository.save(user);
    }

    // Desarquiva a worksheet de um usuario
    public void unarchiveWorksheet(Worksheet worksheet, User user){

        user.getArchivedWorksheets().remove(worksheet);
        user.getWorksheets().add(worksheet);

        userRepository.save(user);
    }
}

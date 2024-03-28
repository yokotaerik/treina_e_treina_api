package com.yokota.treino.controllers;

import com.yokota.treino.dtos.worksheet.CreateWorkoutDTO;
import com.yokota.treino.model.user.User;
import com.yokota.treino.model.worksheet.Worksheet;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.WorksheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/worksheet")
public class WorksheetController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    WorksheetService worksheetService;

    @PostMapping("/add")
    public ResponseEntity<?> addWorksheet(@RequestBody @Valid CreateWorkoutDTO data) {
            var user = authorizationService.getCurrentUser();
            worksheetService.createNewWorksheet(data, user);
            return ResponseEntity.ok("Workout template created!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(@PathVariable Long id) {
        Worksheet worksheet = worksheetService.findById(id);

        var response = worksheetService.returnWorksheet(worksheet);

        return ResponseEntity.ok(response);
    }



//APENAS ADMINS A FIM DE APAGAR TODOS OS DADOS RELATIVOS A PLANILHA
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteWorksheet(@PathVariable Long id) throws Exception {
//        User user = authorizationService.getCurrentUser();
//
//        Worksheet worksheet = worksheetService.findById(id);
//
//        worksheetService.deleteWorksheet(worksheet, user);
//
//        return ResponseEntity.ok("DELETED");
//    }

    @PatchMapping("/archive/{id}")
    public ResponseEntity<?> archiveWorksheet(@PathVariable Long id)  {
        User user = authorizationService.getCurrentUser();

        Worksheet worksheet = worksheetService.findById(id);

        worksheetService.archiveWorksheet(worksheet, user);

        return ResponseEntity.ok("ARCHIVED");
    }

    @PatchMapping("/unarchive/{id}")
    public ResponseEntity<?> unarchiveWorksheet(@PathVariable Long id)  {
        User user = authorizationService.getCurrentUser();

        Worksheet worksheet = worksheetService.findById(id);

        worksheetService.unarchiveWorksheet(worksheet, user);

        return ResponseEntity.ok("Unarchive");
    }

}

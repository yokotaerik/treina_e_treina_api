package com.yokota.treino.controllers;

import com.yokota.treino.model.set.PatchSetDTO;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/set")
public class SetController {

    @Autowired
    SetService setService;

    @Autowired
    AuthorizationService authorizationService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchSet(@RequestBody PatchSetDTO data, @PathVariable Long id) throws Exception {

        var user = authorizationService.getCurrentUser();

        var set = setService.findById(id);

        setService.patchSet(set, data, user);

        return ResponseEntity.ok("Set patched");
    }
}

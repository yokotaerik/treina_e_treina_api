package com.yokota.treino.controllers;

import com.yokota.treino.dtos.set.PatchSetDTO;
import com.yokota.treino.service.AuthorizationService;
import com.yokota.treino.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/set")
public class SetController {

    @Autowired
    SetService setService;

    @Autowired
    AuthorizationService authorizationService;

    @PatchMapping("/update")
    public ResponseEntity<?> patchSet(@RequestBody List<PatchSetDTO> data) throws Exception {

        var user = authorizationService.getCurrentUser();

        setService.patchSetList(data, user);

        return ResponseEntity.ok("Set patched");
    }

}

package com.yokota.treino.service;

import com.yokota.treino.dtos.set.PatchSetDTO;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.user.User;
import com.yokota.treino.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetService {

    private final SetRepository setRepository;

    @Autowired
    public SetService(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    public Set findById(Long id) {
        return setRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Set with ID " + id + " not found."));
    }

    public void patchSetList(List<PatchSetDTO> data, User user) {
        for (PatchSetDTO patchSetDTO : data) {
            patchSet(patchSetDTO, user);
        }
    }

    public void patchSet(PatchSetDTO data, User user) {
        Set set = findById(data.id());

        if (!set.getWorkout().getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission");
        }

        if (data.minutesResting() >= 0) {
            set.setMinutesResting(data.minutesResting());
        }
        if (data.reps() > 0) {
            set.setReps(data.reps());
        }
        if (data.weight() > 0) {
            set.setWeight(data.weight());
        }
        if (data.notes() != null) {
            set.setNotes(data.notes());
        }

        setRepository.save(set);
    }

}

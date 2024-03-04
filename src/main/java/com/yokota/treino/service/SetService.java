package com.yokota.treino.service;

import com.yokota.treino.model.set.PatchSetDTO;
import com.yokota.treino.model.set.Set;
import com.yokota.treino.model.user.User;
import com.yokota.treino.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetService {

        @Autowired
        SetRepository setRepository;

        public Set findById(Long id){
            return setRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Set with ID " + id + " not found."));
        }


    public void patchSet(Set set, PatchSetDTO data, User user) throws Exception {

        if(!set.getWorkout().getUser().equals(user)){
            throw new Exception("You do not have permission");
        }

        if (data != null) {
            if (data.minutesResting() >= 0) {
                set.setMinutesResting(data.minutesResting());
            }
            if (data.reps() > 0) {
                set.setReps(data.reps());
            }
            if (data.weight() > 0) {
                set.setWeight(data.weight());
            }
        } else {
            throw new IllegalArgumentException("Invalid information's ");
        }

        setRepository.save(set);
    }
}

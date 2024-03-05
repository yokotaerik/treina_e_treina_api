package com.yokota.treino.service;

import com.yokota.treino.model.user.User;
import com.yokota.treino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
    }
}

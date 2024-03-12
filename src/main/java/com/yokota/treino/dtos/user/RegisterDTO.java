package com.yokota.treino.dtos.user;

import com.yokota.treino.model.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}

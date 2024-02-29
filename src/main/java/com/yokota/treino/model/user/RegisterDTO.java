package com.yokota.treino.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}

package com.br.dto;

import com.br.entities.User;

public record AuthenticatedResponse(String token, User user) {
}

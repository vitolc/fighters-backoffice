package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank(message = "A email is required to login" ) String email,
                              @NotBlank(message = "A password is required to login") String password) { }

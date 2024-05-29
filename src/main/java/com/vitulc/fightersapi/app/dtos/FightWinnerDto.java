package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FightWinnerDto(@NotNull(message = "fight id is required") Long fightId,
                             @NotBlank(message = "The fighter's document is required") String document){
}

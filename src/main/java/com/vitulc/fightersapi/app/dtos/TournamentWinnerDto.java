package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TournamentWinnerDto(@NotNull(message = "fight id is required") Long tournamentId,
                                  @NotBlank(message = "The fighter's document is required") String document){
}

package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TournamentFighterDto(
        @NotNull(message = "You need the tournament ID to add the fighter") Long tournamentId,
        @NotBlank(message = "The document is required to add the fighter to the tournament") String document) {
}

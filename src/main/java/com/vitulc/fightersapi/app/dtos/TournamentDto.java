package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record TournamentDto (
        @NotBlank(message = "Name is required to make a tournament") String tournamentName){
}

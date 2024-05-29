package com.vitulc.fightersapi.app.dtos;

import com.vitulc.fightersapi.app.entities.Tournament;

public record TournamentResponseDto(
        String tournamentName,
        Long tournamentId){

    public TournamentResponseDto(Tournament tournament){
        this(
                tournament.getName(),
                tournament.getId());
    }
}

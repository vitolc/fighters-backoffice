package com.vitulc.fightersapi.app.dtos;

import java.util.List;

public record TournamentDetailsDto(
        String tournamentName,
        Long tournamentId,
        List<FighterResponseDto> fighters,
        List<FightsHistoryDto> fights,
        String tournamentWinnerName,
        String tournamentWinnerDocument){
}

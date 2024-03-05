package com.vitulc.fightersapi.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitulc.fightersapi.app.entities.FightInfo;

import java.time.LocalDateTime;

public record FightsHistoryDto(Long fightId, FighterResponseDto fighterOne, FighterResponseDto fighterTwo,
                               String categoryName, String tournamentName,
                               @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC") LocalDateTime date,
                               String winnerName, String winnerDocument ) {

    public FightsHistoryDto(FightInfo fightInfo) {
        this(fightInfo.getFightId(),
                FighterResponseDto.of(
                        fightInfo.getFighterOneDocument(),
                        fightInfo.getFighterOneName(),
                        fightInfo.getFighterOneNickname(),
                        fightInfo.getFighterOneAge(),
                        fightInfo.getFighterOneWeight()),

                FighterResponseDto.of(
                        fightInfo.getFighterTwoDocument(),
                        fightInfo.getFighterTwoName(),
                        fightInfo.getFighterTwoNickname(),
                        fightInfo.getFighterTwoAge(),
                        fightInfo.getFighterTwoWeight()),

                fightInfo.getCategoryName(),
                fightInfo.getTournamentName(),
                fightInfo.getDate(),
                fightInfo.getWinnerName(),
                fightInfo.getWinnerDocument());
    }
}

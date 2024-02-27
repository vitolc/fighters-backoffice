package com.vitulc.fightersapi.app.dtos;

import com.vitulc.fightersapi.app.entities.FightInfo;

public record FightsHistoryDto(Long fightId, String fighterOne, String fighterTwo, String winner){

    public FightsHistoryDto(FightInfo fightInfo){

        this(   fightInfo.getFightId(),
                fightInfo.getFighterOneName(),
                fightInfo.getFighterTwoName(),
                fightInfo.getWinnerName());
    }
}

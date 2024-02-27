package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record FightDto(@NotBlank(message = "The fighter's document is necessary to register him for the fight") String FighterOneDocument,
                       @NotBlank(message = "The fighter's document is necessary to register him for the fight") String FighterTwoDocument,
                       Long categoryId,
                       Long tournamentId,
                       String date){
}

package com.vitulc.fightersapi.app.dtos;

import com.vitulc.fightersapi.app.entities.Fighter;

public record FighterResponseDto(
        String document,
        String name,
        String nickname,
        int age,
        Float weight) {

    public FighterResponseDto(Fighter fighter) {
        this(
                fighter.getDocument(),
                fighter.getName(),
                fighter.getNickname(),
                fighter.getAge(),
                fighter.getWeight());
    }

    public static FighterResponseDto of( String document, String name, String nickname, int age,  Float weight) {
        return new FighterResponseDto(document, name, nickname, age, weight);
    }
}

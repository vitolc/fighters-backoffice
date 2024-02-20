package com.vitulc.fightersapi.app.dtos;

import com.vitulc.fightersapi.app.entities.Fighter;

public record FighterResponseDto(
        String document,
        String name,
        String nickname,
        int age,
        Float weight,
        String category) {

    public FighterResponseDto(Fighter fighter) {
        this(
                fighter.getDocument(),
                fighter.getName(),
                fighter.getNickname(),
                fighter.getAge(),
                fighter.getWeight(),
                fighter.getCategory().getCategoryName());
    }
}

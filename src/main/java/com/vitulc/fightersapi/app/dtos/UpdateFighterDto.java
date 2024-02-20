package com.vitulc.fightersapi.app.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateFighterDto(
                               @NotBlank(message = "A Name is required to register a fighter")String name,
                               @NotBlank(message = "A Nickname is required to register a fighter")String nickname,
                               @NotNull(message = "A Age is required to register a fighter")int age,
                               @NotNull(message = "A Weight is required to register a fighter")Float weight,
                               @NotBlank(message = "The fighter's category group must be specified") String categoryGroup){
}

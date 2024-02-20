package com.vitulc.fightersapi.app.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record FighterDto (//@CPF(message = "Invalid Cpf")
                          @NotBlank(message = "A Document is required to register a fighter") String document,
                          @NotBlank(message = "A Name is required to register a fighter")String name,
                          @NotBlank(message = "A Nickname is required to register a fighter")String nickname,
                          @NotNull(message = "A Age is required to register a fighter")int age,
                          @NotNull(message = "A Weight is required to register a fighter")Float weight){
}

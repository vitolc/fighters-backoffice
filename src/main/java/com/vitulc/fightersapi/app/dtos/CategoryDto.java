package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDto(@NotNull (message = "A minimum weight value is required to create the category") Float minWeight,
                          @NotNull (message = "A maximum weight value is required to create the category") Float maxWeight,
                          @NotBlank (message = "Your category needs to have a name") String categoryName,
                          @NotBlank (message = "You need to say which category group you want to add your category to") String categoryGroup){
}

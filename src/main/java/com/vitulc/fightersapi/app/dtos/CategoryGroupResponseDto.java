package com.vitulc.fightersapi.app.dtos;

import com.vitulc.fightersapi.app.entities.CategoryGroup;
import jakarta.validation.constraints.NotBlank;

public record CategoryGroupResponseDto(@NotBlank(message = "Your category group needs to have a name") String categoryGroupName,
                                       Long categoryGroupId){

    public CategoryGroupResponseDto(CategoryGroup categoryGroup) {
        this(
                categoryGroup.getName(),
                categoryGroup.getId());
    }
}

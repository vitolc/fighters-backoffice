package com.vitulc.fightersapi.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryGroupDto (@NotBlank(message = "Your category group needs to have a name") String categoryGroupName){
}

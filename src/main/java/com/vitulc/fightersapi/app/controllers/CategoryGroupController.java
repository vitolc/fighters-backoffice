package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.CategoryDto;
import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import com.vitulc.fightersapi.app.dtos.CategoryGroupResponseDto;
import com.vitulc.fightersapi.app.services.CategoryGroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class CategoryGroupController {

    private final CategoryGroupService categoryGroupService;

    public CategoryGroupController(CategoryGroupService categoryGroupService) {
        this.categoryGroupService = categoryGroupService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createCategoryGroup(@RequestBody @Valid CategoryGroupDto categoryGroupDto){
        return categoryGroupService.createCategoryGroup(categoryGroupDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryGroupResponseDto>> getCategoryGroups() {
        return categoryGroupService.getCategoryGroups();
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategoriesInCategoryGroup(
            @RequestParam("categoryGroupName") String categoryGroupName){
        return categoryGroupService.getAllCategoriesByGroup(categoryGroupName);
    }
}

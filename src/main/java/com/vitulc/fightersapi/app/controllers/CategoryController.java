package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.CategoryDto;
import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import com.vitulc.fightersapi.app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PostMapping("/create/group")
    public ResponseEntity<String> createCategoryGroup(@RequestBody @Valid CategoryGroupDto categoryGroupDto){
        return categoryService.createCategoryGroup(categoryGroupDto);
    }
}

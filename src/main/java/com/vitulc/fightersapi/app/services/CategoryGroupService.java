package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.CategoryDto;
import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import com.vitulc.fightersapi.app.dtos.CategoryGroupResponseDto;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.CategoryGroupRepository;
import com.vitulc.fightersapi.app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryGroupService {

    private final CategoryGroupRepository categoryGroupRepository;
    private final AuthenticationService authenticationService;
    private final CategoryRepository categoryRepository;

    public CategoryGroupService
            (CategoryGroupRepository categoryGroupRepository,
             AuthenticationService authenticationService,
             CategoryRepository categoryRepository) {

        this.categoryGroupRepository = categoryGroupRepository;
        this.authenticationService = authenticationService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ResponseEntity<String> createCategoryGroup(CategoryGroupDto categoryGroupDto){

        if (categoryGroupRepository.findByUserOrUserIsNullAndName(authenticationService.getCurrentUser(),
                categoryGroupDto.categoryGroupName()).isPresent()) {
            throw new BadRequestException(String.format("You already have a category group with name: %s",  categoryGroupDto.categoryGroupName()));
        }

        var categoryGroup = new CategoryGroup(categoryGroupDto);
        categoryGroup.setUser(authenticationService.getCurrentUser());
        categoryGroupRepository.save(categoryGroup);
        return ResponseEntity.ok("Category group created successfully");
    }

    public ResponseEntity<List<CategoryGroupResponseDto>> getCategoryGroups(){

        List<CategoryGroup> categoryGroups = categoryGroupRepository.findByUserOrUserIsNull(authenticationService.getCurrentUser());

        List<CategoryGroupResponseDto> categoryGroupList = categoryGroups.stream()
                .map(CategoryGroupResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryGroupList);
    }

    public ResponseEntity<List<CategoryDto>> getAllCategoriesByGroup(String categoryGroupName) {

        List<Category> categories = categoryRepository.findByCategoryGroupNameAndCategoryGroupUser(categoryGroupName, authenticationService.getCurrentUser());

        if (categories.isEmpty()) {
            throw new NotFoundException("No categories found for the specified category group");
        }

        List<CategoryDto> categoryList = categories.stream()
                .map(category -> new CategoryDto(
                        category.getMinWeight(), category.getMaxWeight(), category.getCategoryName(), category.getCategoryGroup().getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryList);
    }
}

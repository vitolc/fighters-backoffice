package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.dtos.CategoryDto;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.Users;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.CategoryGroupRepository;
import com.vitulc.fightersapi.app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            AuthenticationService authenticationService,
            CategoryGroupRepository categoryGroupRepository) {

        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public ResponseEntity<String> createCategory(CategoryDto categoryDto) {

        var categoryGroup = categoryGroupRepository.findByUserAndName(authenticationService.getCurrentUser(), categoryDto.categoryGroup())
                .orElseThrow(() -> new BadRequestException("You do not have a category group with this name"));

        if (categoryRepository.findByCategoryNameAndCategoryGroup(categoryDto.categoryName(), categoryGroup) != null) {
            throw new BadRequestException("This category group already has a category with this name");
        }

        var category = new Category(categoryDto);
        category.setCategoryGroup(categoryGroup);

        categoryRepository.save(category);
        return ResponseEntity.ok("Category created successfully");
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
        return ResponseEntity.ok(String.format("Category group (%s) created successfully", categoryGroupDto.categoryGroupName()));
    }

    public List<Category> getCategoriesByGroup(CategoryGroup categoryGroup) {
        return categoryRepository.findByCategoryGroup(categoryGroup);
    }

    public Category getCategoryByWeight(List<Category> categories, float weight) {

        for (Category category : categories) {
            if (category.getMinWeight() <= weight && weight <= category.getMaxWeight()) {
                return category;
            }
        }
        throw new BadRequestException("There is no category for this weight in the category group provided");
    }

    public Category getByIdAndCategoryGroupUser(Long id, Users user) {
        return categoryRepository.findByIdAndCategoryGroupUser(id, user)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }


}

package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.FightDto;
import com.vitulc.fightersapi.app.dtos.CategoryDto;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Users;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.CategoryGroupRepository;
import com.vitulc.fightersapi.app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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

    @Transactional
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

    public Category getByIdAndCategoryGroupUser(Long id, Users user) {
        return categoryRepository.findByIdAndCategoryGroupUserOrNull(id, user)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    Category validateCategory(FightDto fightDto, Fighter fighterOne, Fighter fighterTwo) {
        if (fightDto.categoryGroupId() == null) return null;

        Category category = getByIdAndCategoryGroupUser(fightDto.categoryGroupId(), authenticationService.getCurrentUser());

        if (fighterOne.getWeight() < category.getMinWeight() || fighterOne.getWeight() > category.getMaxWeight()) {
            throw new BadRequestException("Fighter one weight is not within the category limits");
        }

        if (fighterTwo.getWeight() < category.getMinWeight() || fighterTwo.getWeight() > category.getMaxWeight()) {
            throw new BadRequestException("Fighter two weight is not within the category limits");
        }

        return category;
    }

}

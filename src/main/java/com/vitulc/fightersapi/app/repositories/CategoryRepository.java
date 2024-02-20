package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryGroup(CategoryGroup categoryGroup);
    Category findByCategoryNameAndCategoryGroup(String categoryName, CategoryGroup categoryGroup);
}

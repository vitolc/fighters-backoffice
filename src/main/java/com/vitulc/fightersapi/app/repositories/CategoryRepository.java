package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.categoryGroup.name = :categoryGroupName AND (c.categoryGroup.user = :user OR c.categoryGroup.user IS NULL)")
    List<Category> findByCategoryGroupNameAndCategoryGroupUser(String categoryGroupName, Users user);

    Category findByCategoryNameAndCategoryGroup(String categoryName, CategoryGroup categoryGroup);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND (c.categoryGroup.user = :user OR c.categoryGroup.user IS NULL)")
    Optional<Category> findByIdAndCategoryGroupUserOrNull(Long id, Users user);


}

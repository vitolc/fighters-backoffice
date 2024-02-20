package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.entities.Users;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    @Query("SELECT c FROM CategoryGroup c WHERE (c.user = :user OR c.user IS NULL) AND c.name = :name")
    Optional<CategoryGroup> findByUserOrUserIsNullAndName(@Param("user") Users user, @Param("name") String name);

    Optional<CategoryGroup> findByUserAndName(@Param("user") Users user, @Param("name") String name);
}


package com.vitulc.fightersapi.app.entities;

import com.vitulc.fightersapi.app.dtos.CategoryDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"category_name", "category_group_id"})
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float minWeight;
    private Float maxWeight;
    private String categoryName;

    @ManyToOne
    private CategoryGroup categoryGroup;

    public Category(CategoryDto categoryDto){
        this.minWeight = categoryDto.minWeight();
        this.maxWeight = categoryDto.maxWeight();
        this.categoryName = categoryDto.categoryName();
    }

    public Category(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Float minValue) {
        this.minWeight = minValue;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxValue) {
        this.maxWeight = maxValue;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryGroup getGroup() {
        return categoryGroup;
    }

    public void setGroup(CategoryGroup categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public CategoryGroup getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroup = categoryGroup;
    }
}

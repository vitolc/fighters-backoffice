package com.vitulc.fightersapi.app.entities;

import com.vitulc.fightersapi.app.dtos.CategoryDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"category_name", "user_id", "category_group_id"})
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

    @OneToMany(mappedBy = "category")
    private List<Fighter> fighters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;


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

    public List<Fighter> getFighters() {
        return fighters;
    }

    public void setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public CategoryGroup getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroup = categoryGroup;
    }
}

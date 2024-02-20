package com.vitulc.fightersapi.app.entities;

import com.vitulc.fightersapi.app.dtos.CategoryGroupDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "user_id"})
)
public class CategoryGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "categoryGroup")
    private List<Category> category = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private String name;

    public CategoryGroup(CategoryGroupDto categoryGroupDto) {
        this.name = categoryGroupDto.categoryGroupName();
    }

    public CategoryGroup() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

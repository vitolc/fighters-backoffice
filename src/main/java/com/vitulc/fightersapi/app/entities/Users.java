package com.vitulc.fightersapi.app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vitulc.fightersapi.app.dtos.UserDto;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Fighter> fighters = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<CategoryGroup> categoryGroup;

    @OneToMany(mappedBy = "user")
    private List<Fight> fights = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FightInfo> fightsInfo = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tournament> tournaments = new ArrayList<>();


    public Users(UserDto userDto, String encryptPassword) {

        this.email = userDto.email();
        this.password = encryptPassword;
        this.username = userDto.username();
    }

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public void setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
    }

    public Set<CategoryGroup> getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(Set<CategoryGroup> categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public List<Fight> getFights() {
        return fights;
    }

    public void setFights(List<Fight> fights) {
        this.fights = fights;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public List<FightInfo> getFightsInfo() {
        return fightsInfo;
    }

    public void setFightsInfo(List<FightInfo> fightsInfo) {
        this.fightsInfo = fightsInfo;
    }
}

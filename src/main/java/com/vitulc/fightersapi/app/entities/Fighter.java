package com.vitulc.fightersapi.app.entities;

import com.vitulc.fightersapi.app.dtos.FighterDto;
import jakarta.persistence.*;

@Entity
public class Fighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;

    private String name;

    private String nickname;

    private int age;

    private Float weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Fighter(FighterDto fighterDto) {
        this.document = fighterDto.document();
        this.name = fighterDto.name();
        this.nickname = fighterDto.nickname();
        this.age = fighterDto.age();
        this.weight = fighterDto.weight();
    }

    public Fighter() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

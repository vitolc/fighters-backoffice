package com.vitulc.fightersapi.app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class FightInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Long fightId;

    private String fighterOneName;
    private String fighterOneDocument;
    private int fighterOneAge;
    private String fighterOneNickname;
    private Float fighterOneWeight;

    private String fighterTwoName;
    private String fighterTwoDocument;
    private int fighterTwoAge;
    private String fighterTwoNickname;
    private Float fighterTwoWeight;

    private String categoryName;
    private String tournamentName;
    private String winnerName;
    private String winnerDocument;
    private LocalDateTime date;

    public FightInfo(Fighter fighterOne, Fighter fighterTwo) {
        this.fighterOneName = fighterOne.getName();
        this.fighterOneDocument = fighterOne.getDocument();
        this.fighterOneAge = fighterOne.getAge();
        this.fighterOneNickname = fighterOne.getNickname();
        this.fighterOneWeight = fighterOne.getWeight();

        this.fighterTwoName = fighterTwo.getName();
        this.fighterTwoDocument = fighterTwo.getDocument();
        this.fighterTwoAge = fighterTwo.getAge();
        this.fighterTwoNickname = fighterTwo.getNickname();
        this.fighterTwoWeight = fighterTwo.getWeight();

    }

    public FightInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getFightId() {
        return fightId;
    }

    public void setFightId(Long fightId) {
        this.fightId = fightId;
    }

    public String getFighterOneName() {
        return fighterOneName;
    }

    public void setFighterOneName(String fighterOneName) {
        this.fighterOneName = fighterOneName;
    }

    public String getFighterOneDocument() {
        return fighterOneDocument;
    }

    public void setFighterOneDocument(String fighterOneDocument) {
        this.fighterOneDocument = fighterOneDocument;
    }

    public int getFighterOneAge() {
        return fighterOneAge;
    }

    public void setFighterOneAge(int fighterOneAge) {
        this.fighterOneAge = fighterOneAge;
    }

    public String getFighterOneNickname() {
        return fighterOneNickname;
    }

    public void setFighterOneNickname(String fighterOneNickname) {
        this.fighterOneNickname = fighterOneNickname;
    }

    public Float getFighterOneWeight() {
        return fighterOneWeight;
    }

    public void setFighterOneWeight(Float fighterOneWeight) {
        this.fighterOneWeight = fighterOneWeight;
    }

    public String getFighterTwoName() {
        return fighterTwoName;
    }

    public void setFighterTwoName(String fighterTwoName) {
        this.fighterTwoName = fighterTwoName;
    }

    public String getFighterTwoDocument() {
        return fighterTwoDocument;
    }

    public void setFighterTwoDocument(String fighterTwoDocument) {
        this.fighterTwoDocument = fighterTwoDocument;
    }

    public int getFighterTwoAge() {
        return fighterTwoAge;
    }

    public void setFighterTwoAge(int fighterTwoAge) {
        this.fighterTwoAge = fighterTwoAge;
    }

    public String getFighterTwoNickname() {
        return fighterTwoNickname;
    }

    public void setFighterTwoNickname(String fighterTwoNickname) {
        this.fighterTwoNickname = fighterTwoNickname;
    }

    public Float getFighterTwoWeight() {
        return fighterTwoWeight;
    }

    public void setFighterTwoWeight(Float fighterTwoWeight) {
        this.fighterTwoWeight = fighterTwoWeight;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinnerDocument() {
        return winnerDocument;
    }

    public void setWinnerDocument(String winnerDocument) {
        this.winnerDocument = winnerDocument;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

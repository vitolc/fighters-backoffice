package com.vitulc.fightersapi.app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fighter_one_id")
    Fighter fighterOne;

    @ManyToOne
    @JoinColumn(name = "fighter_two_id")
    Fighter fighterTwo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "winner_id")
    Fighter winner;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "tournament_id", nullable = true)
    Tournament tournament;

    @Column(nullable = true)
    private LocalDateTime date;


    public Fight(Fighter fighterOne, Fighter fighterTwo, Category category, LocalDateTime date, Tournament tournament) {
        this.fighterOne = fighterOne;
        this.fighterTwo = fighterTwo;
        this.category = category;
        this.date = date;
        this.tournament = tournament;
    }

    public Fight() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fighter getFighterOne() {
        return fighterOne;
    }

    public void setFighterOne(Fighter fighterOne) {
        this.fighterOne = fighterOne;
    }

    public Fighter getFighterTwo() {
        return fighterTwo;
    }

    public void setFighterTwo(Fighter fighterTwo) {
        this.fighterTwo = fighterTwo;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Fighter getWinner() {
        return winner;
    }

    public void setWinner(Fighter winner) {
        this.winner = winner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}

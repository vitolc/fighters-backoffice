package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.entities.Fight;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FightRepository extends JpaRepository<Fight, Long> {

    boolean existsByWinnerAndUserAndId(Fighter winner, Users user, Long id);
    Optional<Fight> findByIdAndUser(Long id, Users user);
}


